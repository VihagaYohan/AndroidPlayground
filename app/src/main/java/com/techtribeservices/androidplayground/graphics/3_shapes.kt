package com.techtribeservices.androidplayground.graphics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StampedPathEffectStyle.Companion.Morph
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.techtribeservices.androidplayground.ui.theme.AndroidPlaygroundTheme

@Composable
fun Shapes() {
    morphShapes()
}

@Composable
private fun drawRoundedPolygon() {
    Box(modifier = Modifier
        .drawWithCache {
            val roundedPolygon = RoundedPolygon(
                numVertices = 6,
                radius = size.minDimension / 2,
                centerX = size.width / 2,
                centerY = size.height / 2,
                rounding = CornerRounding(
                    size.minDimension / 10f,
                    smoothing = 0.75f
                )
            )
            val roundedPolygonPath = roundedPolygon
                .toPath()
                .asComposePath()
            onDrawBehind {
                drawPath(
                    roundedPolygonPath,
                    color = Color.Blue
                )
            }
        }
        .fillMaxSize()) { }
}

@Composable
private fun smoothing() {
    Box(
        modifier = Modifier
            .drawWithCache {
                val roundedPolygon = RoundedPolygon(
                    numVertices = 3,
                    radius = size.minDimension / 2,
                    centerX = size.width / 2,
                    centerY = size.height / 2,
                    rounding = CornerRounding(
                        size.minDimension / 10f,
                        smoothing = 0.1f
                    )
                )
                val roundedPolygonPath = roundedPolygon
                    .toPath()
                    .asComposePath()
                onDrawBehind {
                    drawPath(roundedPolygonPath, color = Color.Black)
                }
            }
            .size(100.dp)
    )
}

@Composable
private fun morphShapes() {
    var progress by remember { mutableFloatStateOf(0f) }

    Column {
        Box(
            modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            Column {
                Slider(
                    value = progress,
                    onValueChange = {progress = it},
                    valueRange = 0f..1f,
                    steps = 10
                )

                Text(text = "Slider progress: ${progress.toString()}")
            }
        }

        Box(
            modifier = Modifier
                .drawWithCache {
                    val triangle = RoundedPolygon(
                        numVertices = 3,
                        radius = size.minDimension / 2f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f,
                        rounding = CornerRounding(
                            size.minDimension / 10f,
                            smoothing = 0.1f
                        )
                    )

                    val square = RoundedPolygon(
                        numVertices = 4,
                        radius = size.minDimension / 2f,
                        centerX = size.width / 2f,
                        centerY = size.height / 2f
                    )

                    val morph = Morph(start = triangle, end = square)
                    val morphPath = morph
                        .toPath(progress = progress)
                        .asComposePath()

                    onDrawBehind {
                        drawPath(
                            morphPath,
                            color = Color.Black
                        )
                    }
                }
                .fillMaxSize()
        ) {
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ShapesPreview() {
    AndroidPlaygroundTheme {
        Shapes()
    }
}