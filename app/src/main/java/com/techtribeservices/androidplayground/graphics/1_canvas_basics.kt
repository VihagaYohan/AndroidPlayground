package com.techtribeservices.androidplayground.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.techtribeservices.androidplayground.ui.theme.AndroidPlaygroundTheme

@Composable
fun CanvasBasics() {
    drawTriangle()
}

@Composable
private fun drawRectangle() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasQuadrantSize = size / 2f
        drawRect(
            color = Color.Red,
            size = canvasQuadrantSize,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.cornerPathEffect(10.dp.toPx())
            ),
        )
    }
}

@Composable
private fun drawLines() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawLine(
            start = Offset(x = canvasWidth, y = 0f),
            end = Offset(x = 0f, y = canvasHeight),
            color = Color.Red,
            strokeWidth = 40.dp.toPx(),
            pathEffect = PathEffect.chainPathEffect(
                outer = PathEffect.cornerPathEffect(10.dp.toPx()),
                inner = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        )
    }
}

@Composable
private fun scaleMethod() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        scale(
            scaleX = 3f,
            scaleY = 2f
        ) {
            drawCircle(
                Color.Blue,
                radius = 50.dp.toPx()
            )
        }
    }
}

@Composable
private fun translateMethod() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        translate(
            top = -size.height / 2,
            left = size.width / 2
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100.dp.toPx(),
                center = center,
                colorFilter = ColorFilter.tint(
                    Color.Blue,
                    blendMode = BlendMode.Color
                )
            )
        }
    }
}

@Composable
private fun rotateMethod() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        rotate(degrees = 90F) {
            drawRoundRect(
                color = Color.Magenta,
                size = size / 3f,
                topLeft = Offset(
                    x = size.width / 3f,
                    y = size.height / 3f
                ),
                cornerRadius = CornerRadius(10.dp.toPx()),
            )
        }
    }
}

@Composable
private fun drawCustomText() {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        drawText(
            textMeasurer = textMeasurer,
            text = "Hello, world!",
            topLeft = Offset(
                x = size.width / 3,
                y = size.height / 2
            )
        )
    }
}

@Composable
private fun measureText() {
    val textMeasurer = rememberTextMeasurer()
    val longTextExample = "Hello, world!, Welcome to Canvas tutorial on Jetpack compose"

    Spacer(
        modifier = Modifier
            .drawWithCache {
                val measuredText =
                    textMeasurer.measure(
                        AnnotatedString(longTextExample),
                        constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                        style = TextStyle(fontSize = 18.sp)
                    )

                onDrawBehind {
                    drawRect(Color.Red, size = measuredText.size.toSize())
                    drawText(measuredText)
                }
            }
            .fillMaxSize()
    )
}

@Composable
private fun drawTriangle() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(x = canvasWidth / 2, y = canvasHeight / 2)
        path.lineTo(x = canvasWidth, y = 0f)
        path.close()

        drawPath(
            path = path,
            color = Color.Magenta,
            style = Stroke(
                width = 5.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasBasicsPreview() {
    AndroidPlaygroundTheme {
        CanvasBasics()
    }
}