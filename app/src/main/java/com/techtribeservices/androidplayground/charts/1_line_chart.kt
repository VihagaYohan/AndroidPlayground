package com.techtribeservices.androidplayground.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techtribeservices.androidplayground.ui.theme.AndroidPlaygroundTheme
import com.techtribeservices.androidplayground.ui.theme.Purple40
import com.techtribeservices.androidplayground.ui.theme.Purple80
import com.techtribeservices.androidplayground.ui.theme.PurpleGrey80

data class ChartData(
    val x: String,
    val y: Number
)

@Composable
fun LineChart() {
    val chartData = listOf(
        ChartData(x = "USA", 100),
        ChartData(x = "RUS", 200),
        ChartData(x = "AUS", 60),
        ChartData(x = "IND", y = 300),
        ChartData(x = "ISR", y = 350),
        ChartData(x = "SLK", y = 700),
        ChartData(x = "PAK", y = 50)
    )

    Box(
        modifier = Modifier
            .background(Purple40)
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .padding(8.dp)
                .aspectRatio(3/2f)
                .fillMaxSize()
                .align(Alignment.Center)
                .drawWithCache {
                    val path = generatePath(chartData, size)
                    val filledPath = Path()
                    filledPath.addPath(path)
                    filledPath.relativeLineTo(0f, size.height)
                    filledPath.lineTo(0f, size.height)
                    filledPath.close()

                    onDrawBehind {
                        val barWidthPx = 1.dp.toPx()
                        val barColor = Color.White.copy(alpha = 0.5f)
                        drawRect(Color.Yellow.copy(alpha = 0.5f), style = Stroke(barWidthPx))

                        // draw vertical lines
                        val verticalLines = 4
                        val verticalSize = size.width / (verticalLines + 1)
                        repeat(verticalLines) {i ->
                            val startX = verticalSize *  (i + 1)
                            drawLine(
                                barColor,
                                start = Offset(startX, 0f),
                                end = Offset(startX, size.height),
                                strokeWidth = barWidthPx
                            )
                        }

                        // draw horizontal lines
                        val horizontalLines = 3
                        val sectionSize = size.height / (horizontalLines + 1)
                        repeat(horizontalLines) {i ->
                            val startY = sectionSize * (i+1)
                            drawLine(
                                barColor,
                                start = Offset(0f, startY),
                                end = Offset(size.width, startY),
                                strokeWidth = barWidthPx
                            )
                        }

                       drawPath(path, Color.Green, style = Stroke(2.dp.toPx()))

                        // draw line
                        drawPath(
                            filledPath,
                            brush = Brush.verticalGradient(
                                listOf(Color.Green.copy(alpha = 0.4f),
                                    Color.Transparent)
                            ),
                            style = Fill
                        )
                    }
                }
        )
    }
}

fun generatePath(data: List<ChartData>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1 // calculates number of segments between the data points
    val xAxisSpacing = size.width / numberEntries // calculates the distance between the data points

    val max = data.maxByOrNull { it?.y?.toFloat() ?: 0f}
    val min = data.minByOrNull { it?.y?.toFloat() ?: 0f }
    val range = max?.y?.toFloat()?.minus(min?.y?.toFloat()!!)
    val heightPxPerAmount = size.height / (range ?: 0f)

    data.forEachIndexed { index, item ->
        if(index == 0) {
            if (min != null) {
                path.moveTo(
                    x = 0f,
                    y = size.height - (item.y.toFloat() - min.y.toFloat()) * heightPxPerAmount)

            }
        }
        val balanceX = index * xAxisSpacing
        val balanceY = size.height - (item.y.toFloat() - min?.y?.toFloat()!!) * heightPxPerAmount
        path.lineTo(x = balanceX, y = balanceY)
    }
    return path
}

@Preview(showBackground = true)
@Composable
fun LineChartPreview() {
    AndroidPlaygroundTheme {
        LineChart()
    }
}