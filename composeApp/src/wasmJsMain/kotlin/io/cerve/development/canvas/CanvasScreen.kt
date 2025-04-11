package io.cerve.development.canvas

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.cerve.development.ui.canvas.component.CerveCanvas
import com.cerve.development.ui.canvas.model.CerveCanvasInteractionType
import com.cerve.development.ui.canvas.operators.rememberCerveDrawCanvasState

@Composable
fun CanvasScreen() {
    MaterialTheme {
        val canvas = rememberCerveDrawCanvasState(initialScale = 2f)

        Scaffold(
            modifier = Modifier,
            bottomBar = {
                BottomAppBar(containerColor = Color.Transparent) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            colors = if (canvas.interactionType.value == CerveCanvasInteractionType.Brush) {
                                IconButtonDefaults.filledIconButtonColors()
                            } else IconButtonDefaults.iconButtonColors(),
                            onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.Brush) }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Outlined.Draw,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            colors = if (canvas.interactionType.value == CerveCanvasInteractionType.AssistedBrush) {
                                IconButtonDefaults.filledIconButtonColors()
                            } else IconButtonDefaults.iconButtonColors(),
                            onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.AssistedBrush) }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Outlined.DesignServices,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            colors = if (canvas.interactionType.value == CerveCanvasInteractionType.SegmentedBrush) {
                                IconButtonDefaults.filledIconButtonColors()
                            } else IconButtonDefaults.iconButtonColors(),
                            onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.SegmentedBrush) }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            colors = if(canvas.interactionType.value == CerveCanvasInteractionType.Eraser) {
                                IconButtonDefaults.filledIconButtonColors()
                            } else IconButtonDefaults.iconButtonColors(),
                            onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.Eraser) }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Default.CleaningServices,
                                contentDescription = null
                            )
                        }
                        IconButton(canvas::onClear) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            CerveCanvas(
                modifier = Modifier
                    .padding(innerPadding)
                    .clip(RectangleShape)
                    .fillMaxSize(),
                strokeWidth = 4.dp,
                gridColor = Color.Black,
                gridLineCount = 50,
                showGridLine = true,
                currentLines = canvas.currentLines,
                currentLineCandidates = canvas.currentLineCandidates,
                interactionType = { canvas.interactionType.value },
                eraserRadius = canvas.eraserRadius,
                eraserCenter = { canvas.eraserCenter.value },
                transformationScale = { canvas.transformationScale.floatValue },
                transformationRotation = { canvas.transformationRotation.value },
                translationAmount = { canvas.transformationOffset.value },
                onChangeEraserCenter = canvas::onChangeEraserCenter,
                onChangeTransformationScale = canvas::onChangeTransformationScale,
                onChangeTransformationRotation = canvas::onChangeTransformationRotation,
                onChangeTransformationOffset = canvas::onChangeTransformationOffset,
                onResetTransformations = canvas::onResetAllTransformation
            )
        }

        AnimatedVisibility (canvas.interactionType.value == CerveCanvasInteractionType.None) {
            Card(modifier = Modifier.padding(12.dp)) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Please select a draw type"
                )
            }
        }

    }
}
