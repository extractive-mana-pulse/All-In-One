package com.example.presentation.details.components

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.allinone.core.presentation.R
import com.example.domain.model.CourseDetails
import com.example.presentation.components.CustomAlertDialog
import com.example.presentation.components.PrimaryButton
import com.example.presentation.toastMessage

@Composable
internal fun DetailsItem(course: CourseDetails) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            AsyncImage(
                model = course.imageUrl ?: R.drawable.compose_logo,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    Text(
                        text = course.author ?: "John Doe",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                            letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                        )
                    )
                    Text(
                        text = course.publishedDate ?: "Published · Oct, 28 2025",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                            lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                            letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        ),
                    )
                }
                TextButton(
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "In progress. This feature will be implemented soon"
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.follow)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = course.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                ),
            )

            val intent = if (course.web_url.isNullOrEmpty()) {
                Intent(
                    Intent.ACTION_VIEW,
                    "https://www.example.com".toUri()
                )
            } else {
                Intent(
                    Intent.ACTION_VIEW,
                    course.web_url!!.toUri()
                )
            }

            val openAlertDialog = remember { mutableStateOf(false) }

            PrimaryButton(
                onClick = {
                    openAlertDialog.value = true
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = stringResource(R.string.take_codelab_btn_text)
                )
            }

            if (openAlertDialog.value) {

                CustomAlertDialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                    },
                    onConfirmation = {
                        openAlertDialog.value = false
                        context.startActivity(intent)
                    },
                    dialogTitle = stringResource(R.string.warning),
                    dialogText = stringResource(R.string.dialog_txt),
                    icon = painterResource(R.drawable.outline_info_24),
                    confirmText = stringResource(R.string.confirm),
                    dismissText = stringResource(R.string.dismiss)
                )
            }
        }
    }
}