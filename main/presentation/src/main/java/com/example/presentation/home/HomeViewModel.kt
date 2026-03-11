package com.example.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.CodelabsRepository
import com.example.domain.repository.LeetcodeRepository
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val codelabsRepository: CodelabsRepository,
    private val leetcodeRepository: LeetcodeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _event = Channel<HomeEvent>()
    val event = _event.receiveAsFlow()

    init {
        loadCodelabs()
        loadLeetcode()
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnSearchQueryChange -> {
                val query = action.query

                val codelabsFiltered = if (query.isNotBlank()) {
                    _state.value.codelabs.filter { codelab ->
                        codelab.title.contains(query, ignoreCase = true) ||
                                codelab.subtitle.contains(query, ignoreCase = true) ||
                                codelab.author.contains(query, ignoreCase = true)
                    }
                } else {
                    _state.value.codelabs
                }

                val leetcodeFiltered = if (query.isNotBlank()) {
                    _state.value.leetcodeAlgorithms.filter { algorithm ->
                        algorithm.title.contains(query, ignoreCase = true) ||
                                algorithm.category.contains(query, ignoreCase = true) ||
                                algorithm.difficulty.contains(query, ignoreCase = true)
                    }
                } else {
                    _state.value.leetcodeAlgorithms
                }

                _state.update {
                    it.copy(
                        searchQuery = query,
                        codelabsFiltered = codelabsFiltered,
                        leetcodeFiltered = leetcodeFiltered
                    )
                }
            }

            HomeScreenAction.ClearSearch -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                        codelabsFiltered = it.codelabs,
                        leetcodeFiltered = it.leetcodeAlgorithms
                    )
                }
            }
            else -> Unit
        }
    }

    private fun loadCodelabs() {
        codelabsRepository
            .getAllAlgorithms()
            .distinctUntilChanged()
            .onEach { codelabs ->
                _state.update {
                    it.copy(
                        codelabs = codelabs,
                        codelabsFiltered = if (it.searchQuery.isBlank()) codelabs
                        else codelabs.filter { codelab ->
                            codelab.title.contains(it.searchQuery, ignoreCase = true) ||
                                    codelab.subtitle.contains(it.searchQuery, ignoreCase = true) ||
                                    codelab.author.contains(it.searchQuery, ignoreCase = true)
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

    private fun loadLeetcode() {
        leetcodeRepository
            .getAllAlgorithms()
            .distinctUntilChanged()
            .onEach { algorithms ->
                _state.update {
                    it.copy(
                        leetcodeAlgorithms = algorithms,
                        leetcodeFiltered = if (it.searchQuery.isBlank()) algorithms
                        else algorithms.filter { algorithm ->
                            algorithm.title.contains(it.searchQuery, ignoreCase = true) ||
                                    algorithm.category.contains(it.searchQuery, ignoreCase = true) ||
                                    algorithm.difficulty.contains(it.searchQuery, ignoreCase = true)
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

    fun getAlgorithmById(id: String) = leetcodeRepository.getLeetCodeById(id)
}