package com.techtribeservices.androidplayground.graphics

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techtribeservices.androidplayground.ui.theme.AndroidPlaygroundTheme
import java.time.format.TextStyle

@Composable
fun GraphicModifiers() {
    clipAndShape()
}

@Composable
private fun drawWithContent() {
    var pointerOffset = remember {
        mutableStateOf(Offset(0f,0f))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput("dragging") {
                detectDragGestures { change, dragAmount ->
                    pointerOffset.value += dragAmount
                }
            }
            .onSizeChanged {
                pointerOffset.value = Offset(it.width/2f, it.height/2f)
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = pointerOffset.value,
                        radius = 100.dp.toPx()
                    )
                )
            }
    ) {

    }
}

@Composable
private fun drawBehind() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Android development with Jetpack compose",
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = Color.Magenta.copy(alpha = 0.5f),
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(10.dp)
        )
    }
}

@Composable
private fun clipAndShape() {
    Column(modifier = Modifier.padding(16.dp)) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                }
                .background(Color.Red)
        ) {
            Text("Hello compoose",
                style = androidx.compose.ui.text.TextStyle(color = Color.Black, fontSize = 46.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color.Blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GraphicModifiersPreview() {
    AndroidPlaygroundTheme {
        GraphicModifiers()
    }
}