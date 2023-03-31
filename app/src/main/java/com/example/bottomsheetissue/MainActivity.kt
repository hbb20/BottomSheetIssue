package com.example.bottomsheetissue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.bottomsheetissue.ui.theme.BottomSheetIssueTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        configureStatusBar()
        setContent {
            BottomSheetIssueTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FullScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun FullScreen() {

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val resultBottomSheetState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
            )
            val peekHeight = 100.dp

            val bottomSheetOpenFraction by remember {
                derivedStateOf {
                    resultBottomSheetState.bottomSheetState.openFraction()
                }
            }

            val cornerSize = 16.dp.times(1f - bottomSheetOpenFraction)

            BottomSheetScaffold(
                sheetContent = {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.primary)
                    ) {
                        repeat(50) { counter ->
                            item {
                                Text(
                                    text = "Item $counter",
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                },
                scaffoldState = resultBottomSheetState,
                sheetShape = RoundedCornerShape(topEnd = cornerSize, topStart = cornerSize),
                sheetPeekHeight = peekHeight,
                modifier = Modifier.onPlaced { it.size.height }
            ) {
                Text(
                    text = "Content here",
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .background(MaterialTheme.colors.surface)
                        .padding(8.dp)
                        .align(Alignment.BottomEnd),
                )
            }
            val formatPattern = "%.3f"
            Text(
                text = "State: ${resultBottomSheetState.bottomSheetState.currentValue.name}\n" +
                        "Progress: ${formatPattern.format(resultBottomSheetState.bottomSheetState.progress)}\n" +
                        "Open Fraction: ${formatPattern.format(resultBottomSheetState.bottomSheetState.openFraction())}",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.surface
                    )
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
            )
        }
    }

    fun configureStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.isAppearanceLightStatusBars = true
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
    }
}

/**
 * Function ad
 */
@OptIn(ExperimentalMaterialApi::class)
fun BottomSheetState.openFraction(): Float {
    return (if (progress == 1f && currentValue == BottomSheetValue.Collapsed) {
        0f
    } else if (progress == 1f && currentValue == BottomSheetValue.Expanded) {
        1f
    } else if (currentValue == BottomSheetValue.Collapsed) {
        progress
    } else {
        1 - progress
    }).coerceIn(0f, 1f)
}

/**
 * This is the function that used to work seamlessly before BOM-2023.03.00
 */
//@OptIn(ExperimentalMaterialApi::class)
//fun BottomSheetState.openFraction(): Float {
//    return if (progress.to == BottomSheetValue.Expanded) {
//        progress.fraction
//    } else {
//        1f - progress.fraction
//    }
//}


