package com.example.presentation.autoNight.components

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.autoNight.vm.LanguageViewModel

@Composable
internal fun RadioButtonSingleSelection(
    viewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()

    Column(Modifier.selectableGroup()) {
        viewModel.languageMapping.keys.forEach { language ->
            LanguageRadioButton(
                language = language,
                isSelected = language == selectedLanguage,
                onLanguageSelected = {
                    viewModel.changeLanguage(language)

                    // Restart app to apply language changes
                    val intent = context.packageManager
                        .getLaunchIntentForPackage(context.packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
private fun LanguageRadioButton(
    language: String,
    isSelected: Boolean,
    onLanguageSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = isSelected,
                onClick = onLanguageSelected,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = language,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}