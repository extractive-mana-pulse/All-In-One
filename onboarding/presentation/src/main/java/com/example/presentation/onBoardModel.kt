package com.example.presentation

import com.example.allinone.onboarding.presentation.R
import com.example.domain.OnBoardModel

val onBoardModel = listOf(
    OnBoardModel(
        id = 1,
        title = R.string.all_in_one,
        description = R.string.screen_1_description,
        imageRes = com.example.allinone.core.presentation.R.drawable.ic_launcher_foreground
    ),
    OnBoardModel(
        id = 2,
        title = R.string.code_labs,
        description = R.string.screen_2_description,
        imageRes = R.drawable.android_codelabs
    ),
    OnBoardModel(
        id = 3,
        title = R.string.leetcode,
        description = R.string.screen_3_description,
        imageRes = R.drawable.leetcode
    ),
    OnBoardModel(
        id = 4,
        title = R.string.github,
        description = R.string.screen_4_description,
        imageRes = R.drawable.github_invertocat_black_clearspace
    )
)