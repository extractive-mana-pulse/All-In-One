package com.example.allinone.widget.presentation.screens

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.components.SquareIconButton
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.allinone.R
import com.example.allinone.settings.deviceTemp.domain.model.TemperatureData
import com.example.allinone.widget.domain.repository.GlanceWidgetRepository
import kotlinx.coroutines.flow.Flow

class DeviceTempWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(110.dp, 110.dp)
        private val MEDIUM_RECT = DpSize(250.dp, 110.dp)
        private val LARGE_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(SMALL_SQUARE, MEDIUM_RECT, LARGE_SQUARE)
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = GlanceWidgetRepository.get(context)

        val currentSize = GlanceAppWidgetManager(context).getAppWidgetSizes(id).firstOrNull()

        provideContent {
            GlanceTheme {
                WidgetContent(
                    context = context,
                    tempData = repository.getTemp(),
                    widgetSize = currentSize
                )
            }
        }
    }

    private fun getErrorIntent(context: Context, throwable: Throwable): PendingIntent {
        val intent = Intent(context, DeviceTempWidget::class.java)
        intent.action = "widgetError"
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    override fun onCompositionError(
        context: Context,
        glanceId: GlanceId,
        appWidgetId: Int,
        throwable: Throwable
    ) {
        val rv = RemoteViews(context.packageName, R.layout.error_layout)
        rv.setTextViewText(
            R.id.error_text_view,
            "Error Message: `${throwable.message}`"
        )
        Log.d("Error widget", "Error Message: `${throwable.message}`")
        rv.setOnClickPendingIntent(R.id.error_icon, getErrorIntent(context, throwable))
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, rv)
    }
}

@Composable
fun WidgetContent(
    context: Context,
    tempData: Flow<TemperatureData>,
    widgetSize: DpSize?
) {
    val state = tempData.collectAsState(initial = null)
    val data = state.value

    val isSmallWidget = widgetSize?.let { it.width <= 110.dp } == true
    val isLargeWidget = widgetSize?.let { it.width >= 250.dp && it.height >= 250.dp } == true

    val backgroundColor = when {
        (data?.celsius ?: 0.0f) < 10 -> Color(37, 111, 255)
        (data?.celsius ?: 0.0f) in 10.0..35.0 -> Color(0, 128, 0)
        (data?.celsius ?: 0.0f) in 36.0..45.0 -> Color(255, 222, 33)
        else -> Color(139, 0, 0)
    }

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.widgetBackground)
            .cornerRadius(256.dp)
            .padding(if (isSmallWidget) 4.dp else 8.dp),
    ) {
        Column(
            modifier = GlanceModifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                provider = ImageProvider(R.drawable.device_thermostat),
                contentDescription = null,
                modifier = GlanceModifier
                    .cornerRadius(24.dp)
                    .size(if (isSmallWidget) 32.dp else 48.dp)
                    .background(backgroundColor)
                    .padding(if (isSmallWidget) 8.dp else 16.dp),
            )
            Text(
                text = "${data?.celsius}°C",
                style = TextStyle(
                    color = GlanceTheme.colors.onBackground,
                    fontSize = if (isSmallWidget) 16.sp else 24.sp
                )
            )
        }

        SquareIconButton(
            imageProvider = ImageProvider(R.drawable.refresh_ic),
            contentDescription = null,
            onClick = {
                // Handle refresh action
            },
            modifier = GlanceModifier
                .size(56.dp)
                .padding(16.dp),

            .
            enabled = true
        )
    }
}