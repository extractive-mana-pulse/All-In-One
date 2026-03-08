package com.example.presentation.rich_text_editor.util

import com.example.presentation.R

enum class TextFontFamily(
    val fontResource: Int
) {
    Montserrat(fontResource = R.font.montserrat_regular),
    PTSerif(fontResource = R.font.pt_serif_regular);

    fun label(): String = when (this) {
        Montserrat -> "Mnt"
        PTSerif -> "Ser"
    }

    fun displayName(): String = when (this) {
        Montserrat -> "Montserrat"
        PTSerif -> "PT Serif"
    }
}