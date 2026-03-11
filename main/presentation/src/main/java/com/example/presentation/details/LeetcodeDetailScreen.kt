package com.example.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.domain.model.Leetcode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeetcodeDetailScreen(
    algorithm: Leetcode,
    onNavigateBack: () -> Unit
) {
    val difficultyColor = when (algorithm.difficulty.lowercase()) {
        "easy" -> Color(0xFF00B8A3)
        "medium" -> Color(0xFFFFC01E)
        "hard" -> Color(0xFFFF375F)
        else -> MaterialTheme.colorScheme.outline
    }

    var hintVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // difficulty badge in top bar
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(difficultyColor.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = algorithm.difficulty,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = difficultyColor,
                                fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Header ──────────────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    // category chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = algorithm.category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = algorithm.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "By ${algorithm.author}  ·  ${algorithm.publishedDate}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                        )
                    )
                }
            }

            item { HorizontalDivider() }

            // ── Problem Description ──────────────────────────────
            item {
                DetailSection(title = "Problem") {
                    Text(
                        text = algorithm.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }

            // ── Examples ─────────────────────────────────────────
            item {
                DetailSection(title = "Examples") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = algorithm.examples,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            // ── Hint (collapsible) ────────────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(difficultyColor.copy(alpha = 0.08f))
                        .clickable { hintVisible = !hintVisible }
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.lightbulb_24px),
                                contentDescription = null,
                                tint = difficultyColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Hint",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = difficultyColor,
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                                )
                            )
                        }
                        Icon(
                            painter = painterResource(
                                if (hintVisible) R.drawable.keyboard_arrow_up_24px
                                else R.drawable.keyboard_arrow_down_24px
                            ),
                            contentDescription = null,
                            tint = difficultyColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(visible = hintVisible) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(color = difficultyColor.copy(alpha = 0.2f))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = algorithm.hint,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // ── Open in LeetCode button ───────────────────────────
            item {
                val uriHandler = LocalUriHandler.current
                if (algorithm.leetcodeUrl.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { uriHandler.openUri(algorithm.leetcodeUrl) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = difficultyColor,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.open_in_new_24px),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Solve on LeetCode",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ── Reusable section wrapper ──────────────────────────────────────
@Composable
private fun DetailSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        content()
    }
}