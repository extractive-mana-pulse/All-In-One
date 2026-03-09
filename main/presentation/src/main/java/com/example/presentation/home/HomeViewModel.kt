package com.example.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Codelab
import com.example.domain.repository.CodelabsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeState(
    val codelabs: List<Codelab> = emptyList(),
    val codelabsFiltered: List<Codelab> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed interface HomeAction {
    data class OnSearchQueryChange(val query: String) : HomeAction
    data object ClearSearch : HomeAction
}

sealed interface HomeEvent {
    data class Error(val message: String) : HomeEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val codelabsRepository: CodelabsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()

    init {
        loadCodelabs()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchQueryChange -> {
                val filtered = if (action.query.isNotBlank()) {
                    _state.value.codelabs.filter { codelab ->
                        codelab.title.contains(action.query, ignoreCase = true) ||
                                codelab.subtitle.contains(action.query, ignoreCase = true) ||
                                codelab.author.contains(action.query, ignoreCase = true)
                    }
                } else {
                    _state.value.codelabs
                }

                _state.update {
                    it.copy(
                        searchQuery = action.query,
                        codelabsFiltered = filtered
                    )
                }
            }

            HomeAction.ClearSearch -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        codelabsFiltered = it.codelabs
                    )
                }
            }
        }
    }

    private fun loadCodelabs() {
        codelabsRepository
            .getAllCodelabs()
            .distinctUntilChanged()
            .onEach { codelabs ->
                _state.update {
                    it.copy(
                        codelabs = codelabs,
                        codelabsFiltered = if (it.searchQuery.isBlank()) {
                            codelabs
                        } else {
                            codelabs.filter { codelab ->
                                codelab.title.contains(it.searchQuery, ignoreCase = true) ||
                                        codelab.subtitle.contains(it.searchQuery, ignoreCase = true) ||
                                        codelab.author.contains(it.searchQuery, ignoreCase = true)
                            }
                        },
                        isLoading = false,
                        error = null
                    )
                }
            }
            .catch { throwable ->
                _state.update { it.copy(isLoading = false, error = throwable.message) }
                _event.trySend(HomeEvent.Error(throwable.message ?: "Unknown error"))
            }
            .launchIn(viewModelScope)
    }
}