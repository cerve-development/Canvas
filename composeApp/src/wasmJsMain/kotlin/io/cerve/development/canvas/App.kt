package io.cerve.development.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.outlined.Brush
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility
import com.cerve.development.ui.canvas.component.CerveDrawCanvas
import com.cerve.development.ui.canvas.model.CerveCanvasInteractionType
import com.cerve.development.ui.canvas.operators.rememberCerveDrawCanvasState

@Composable
fun App() {
    MaterialTheme {
        val canvas = rememberCerveDrawCanvasState()

        Scaffold(
            modifier = Modifier,
            bottomBar = {
                BottomAppBar(containerColor = Color.Transparent) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        BadgedBox(
                            badge = {
                                androidx.compose.animation.AnimatedVisibility(canvas.interactionType == null) {
                                    Badge {
                                        Text(
                                            modifier = Modifier.padding(5.dp),
                                            text = "select"
                                        )
                                    }
                                }
                            }
                        ) {
                            IconButton(
                                colors = if (canvas.interactionType == CerveCanvasInteractionType.Brush) {
                                    IconButtonDefaults.filledIconButtonColors()
                                } else IconButtonDefaults.iconButtonColors(),
                                onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.Brush) }
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Default.Draw,
                                    contentDescription = null
                                )
                            }
                        }

                        IconButton(
                            colors = if (canvas.interactionType == CerveCanvasInteractionType.SegmentedBrush) {
                                IconButtonDefaults.filledIconButtonColors()
                            } else IconButtonDefaults.iconButtonColors(),
                            onClick = { canvas.onChangeInteractionType(CerveCanvasInteractionType.SegmentedBrush) }
                        ) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Outlined.Brush,
                                contentDescription = null
                            )
                        }


                        IconButton(
                            colors = if(canvas.interactionType == CerveCanvasInteractionType.Eraser) {
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
                        IconButton(canvas::onUndo) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.AutoMirrored.Default.Undo,
                                contentDescription = null
                            )
                        }
                        IconButton(canvas::onRedo) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.AutoMirrored.Default.Redo,
                                contentDescription = null
                            )
                        }
                        IconButton(canvas::onClear) {
                            Icon(
                                modifier = Modifier,
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            CerveDrawCanvas(
                modifier = Modifier.padding(innerPadding),
                stepSize = 50f,
                strokeWidth = 4.dp,
                interactionType = canvas.interactionType,
                eraserLines = canvas.eraserLines,
                currentLines = canvas.currentLines,
                currentLineCandidates = canvas.currentLineCandidates
            )
        }

        AnimatedVisibility (canvas.interactionType == null) {
            Card(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = "Please select a draw type"
                )
            }
        }
    }
}