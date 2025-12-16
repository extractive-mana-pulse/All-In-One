@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.example.allinone.plcoding.mini_challenges.presentation.december

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.example.allinone.R
import com.example.allinone.ui.theme.bg
import com.example.allinone.ui.theme.evergreenWish
import com.example.allinone.ui.theme.frostyLight
import com.example.allinone.ui.theme.goldenEveGradient
import com.example.allinone.ui.theme.snowyNightGradient
import com.example.allinone.ui.theme.text

@Composable
fun WinterGreetingEditorScreen() {
    val view = LocalView.current
    val context = LocalContext.current
    var selectedTheme by remember { mutableStateOf("Golden Eve") }

    val themeBackgrounds = mapOf(
        "Golden Eve" to goldenEveGradient,
        "Snowy Night" to snowyNightGradient,
        "Evergreen Wish" to null,
        "Frosty Light" to null
    )

    val themeSolidColors = mapOf(
        "Evergreen Wish" to evergreenWish,
        "Frosty Light" to frostyLight
    )

    // Theme decorations with multiple images and their alignments
    data class DecorationItem(
        val drawableRes: Int,
        val alignment: Alignment,
        val contentScale: ContentScale = ContentScale.Fit,
        val modifier: Modifier = Modifier
    )

    val themeDecorations = mapOf(
        "Golden Eve" to listOf(
            DecorationItem(
                R.drawable.golden_eve_decor,
                Alignment.Center,
                ContentScale.FillBounds,
                Modifier.fillMaxSize()
            )
        ),
        "Snowy Night" to listOf(
            DecorationItem(
                R.drawable.snowy_night_decor_top,
                Alignment.TopCenter,
                ContentScale.FillWidth,
                Modifier.fillMaxWidth()
            ),
            DecorationItem(
                R.drawable.clip_path_group,
                Alignment.Center,
                ContentScale.FillBounds,
                Modifier.fillMaxSize()
            ),
            DecorationItem(
                R.drawable.frosty_light_bottom,
                Alignment.BottomCenter,
                ContentScale.FillWidth,
                Modifier.fillMaxWidth()
            )
        ),
        "Evergreen Wish" to listOf(
            DecorationItem(
                R.drawable.evergreen_wish_top_left,
                Alignment.TopStart,
                ContentScale.Fit,
                Modifier.fillMaxWidth(0.5f)
            ),
            DecorationItem(
                R.drawable.evergreen_wish_top_right,
                Alignment.TopEnd,
                ContentScale.Fit,
                Modifier.fillMaxWidth(0.5f)
            ),
            DecorationItem(
                R.drawable.evergreen_wish_bottom,
                Alignment.BottomCenter,
                ContentScale.FillWidth,
                Modifier.fillMaxWidth()
            )
        ),
        "Frosty Light" to listOf(
            DecorationItem(
                R.drawable.frosty_light_top,
                Alignment.TopCenter,
                ContentScale.FillWidth,
                Modifier.fillMaxWidth()
            )
        )
    )

    Scaffold(
        topBar = {
            WinterGreetingEditorTopAppBar(
                selectedTheme = selectedTheme,
                onThemeSelected = { selectedTheme = it }
            )
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .then(
                    if (themeBackgrounds[selectedTheme] != null) {
                        Modifier.background(brush = themeBackgrounds[selectedTheme]!!)
                    } else {
                        Modifier.background(color = themeSolidColors[selectedTheme] ?: Color.Red)
                    }
                )
        ) {
            // Theme decoration overlays
            themeDecorations[selectedTheme]?.forEach { decoration ->
                Image(
                    painter = painterResource(id = decoration.drawableRes),
                    contentDescription = null,
                    modifier = decoration.modifier.align(decoration.alignment),
                    contentScale = decoration.contentScale
                )
            }

            FormattingToolbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
    SideEffect {
        val window = (context as? Activity)?.window
        if (!view.isInEditMode && window != null) {
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true
        }
    }
}

@PreviewLightDark
@Composable
private fun WinterGreetingEditorTopAppBar(
    selectedTheme: String = "Golden Eve",
    onThemeSelected: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Winter Greeting Editor",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = bg,
                titleContentColor = text
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        WinterGreetingThemeDropDownMenu(
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected
        )
    }
}

@Composable
private fun WinterGreetingThemeDropDownMenu(
    selectedTheme: String = "Golden Eve",
    onThemeSelected: (String) -> Unit = {}
) {
    val allOptions = listOf("Golden Eve", "Snowy Night", "Evergreen Wish", "Frosty Light")
    var expanded by remember { mutableStateOf(false) }

    // Filter out the currently selected option from the dropdown list
    val availableOptions = allOptions.filter { it != selectedTheme }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFBFC1E2),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Text(
                    text = selectedTheme,
                    style = MaterialTheme.typography.bodyMedium.copy(color = text)
                )

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = text
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                availableOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onThemeSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
        IconButton(
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFBFC1E2),
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = { /* TODO */ }
        ) {
            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FloatingToolbar() {
    val expanded by remember {
        mutableStateOf(true)
    }
    HorizontalFloatingToolbar(
        expanded = expanded,
        colors = FloatingToolbarDefaults.standardFloatingToolbarColors(),

        ) {
        listOf(
            Icons.Default.FormatBold,
            Icons.Default.FormatItalic,
            Icons.Default.FormatUnderlined,
            Icons.Default.TextFormat,
            Icons.Default.Replay, // stands for reset.
            // add 2 more icons here
        ).forEach { iconRes ->
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = iconRes,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FormattingToolbar(
    modifier: Modifier = Modifier,
) {
    val listOfColors = listOf(
        bg,
        Color(0xFFFFF2E9),
        Color(0xFFF7E6C7),
        Color(0xFF943112),
        text
    )
    val fontNames = listOf(
        "PT Serif",
        "Montserrat"
    )
    val listOfFonts = listOf(
        R.font.pt_serif_regular,
        R.font.montserrat_regular
    )
    val textSizes = listOf("Small", "Medium", "Large")

    var expandedColorPicker by remember {
        mutableStateOf(false)
    }
    var expandedFontPicker by remember {
        mutableStateOf(false)
    }
    var expandedTextSizePicker by remember {
        mutableStateOf(false)
    }
    var selectedColorIndex by remember {
        mutableStateOf(0)
    }
    var selectedFontIndex by remember {
        mutableStateOf(0)
    }
    var selectedTextSizeIndex by remember {
        mutableStateOf(1) // Default to Medium
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = null,
                tint = text
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = null,
                tint = text
            )
        }
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = null,
                tint = text
            )
        }

        // Color picker dropdown
        Box {
            Row(
                modifier = Modifier.clickable { expandedColorPicker = !expandedColorPicker },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            listOfColors[selectedColorIndex],
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
                Icon(
                    imageVector = if (expandedColorPicker) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = text,
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expandedColorPicker,
                onDismissRequest = { expandedColorPicker = false },
                modifier = Modifier.align(Alignment.TopStart),
                offset = DpOffset(0.dp, (-8).dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOfColors.forEachIndexed { index, color ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(color, shape = RoundedCornerShape(8.dp))
                                .border(
                                    width = 2.dp,
                                    color = if (selectedColorIndex == index) Color(0xFFBFC1E2) else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selectedColorIndex = index
                                    expandedColorPicker = false
                                }
                        )
                    }
                }
            }
        }

        // Font picker dropdown
        Box {
            Row(
                modifier = Modifier.clickable { expandedFontPicker = !expandedFontPicker },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fontNames[selectedFontIndex].take(3),
                    style = MaterialTheme.typography.bodySmall.copy(color = text)
                )
                Icon(
                    imageVector = if (expandedFontPicker) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = text,
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expandedFontPicker,
                onDismissRequest = { expandedFontPicker = false },
                modifier = Modifier.align(Alignment.TopStart),
                offset = DpOffset(0.dp, (-8).dp)
            ) {
                listOfFonts.forEachIndexed { index, font ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = fontNames[index],
                                fontFamily = FontFamily(Font(font)),
                                color = text
                            )
                        },
                        onClick = {
                            selectedFontIndex = index
                            expandedFontPicker = false
                        }
                    )
                }
            }
        }

        // Text size picker dropdown
        Box {
            Row(
                modifier = Modifier.clickable { expandedTextSizePicker = !expandedTextSizePicker },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "A",
                    style = MaterialTheme.typography.bodyMedium.copy(color = text)
                )
                Icon(
                    imageVector = if (expandedTextSizePicker) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = text,
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = expandedTextSizePicker,
                onDismissRequest = { expandedTextSizePicker = false },
                modifier = Modifier.align(Alignment.TopStart),
                offset = DpOffset(0.dp, (-8).dp)
            ) {
                textSizes.forEachIndexed { index, size ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = size,
                                color = text
                            )
                        },
                        onClick = {
                            selectedTextSizeIndex = index
                            expandedTextSizePicker = false
                        }
                    )
                }
            }
        }

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Replay,
                contentDescription = null,
                tint = text
            )
        }
    }
}