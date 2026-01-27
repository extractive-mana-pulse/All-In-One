package com.example.presentation.ui.themes

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.allinone.pl_coding.mini_challenges.presentation.R

val challengeBodyLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)
val challengeBodyMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.poppins_black)),
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)
val challengeTitleMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 30.sp
)
val challengeTitleLarge = TextStyle(
    fontFamily = FontFamily(Font(R.font.pt_serif_regular)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 34.sp,
    lineHeight = 42.sp
)
