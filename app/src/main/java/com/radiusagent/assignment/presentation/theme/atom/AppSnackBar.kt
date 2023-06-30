package com.radiusagent.assignment.presentation.theme.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.radiusagent.assignment.R
import com.radiusagent.assignment.presentation.theme.AppShapes
import com.radiusagent.assignment.presentation.theme.AppTypography
import com.radiusagent.assignment.presentation.theme.ErrorSnackbarColor
import com.radiusagent.assignment.presentation.theme.PrimaryTextColor

//region Constants
private const val TEXT_MESSAGE_WEIGHT = 1f
private val BoxPadding = 20.dp
private val TextPaddingStart = 16.dp
private val CloseIconSize = 20.dp
private val CloseIconPaddingStart = 8.dp
private val CloseIconPaddingEnd = 12.dp
//endregion

@Composable
fun SnackBar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = ErrorSnackbarColor,
    textColor: Color = PrimaryTextColor
) {
    Box(
        modifier = modifier
            .padding(BoxPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        SnackbarHost(
            hostState = snackbarHostState
        ) { snackBarData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = backgroundColor, shape = AppShapes.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = snackBarData.visuals.message,
                    modifier = Modifier
                        .padding(start = TextPaddingStart)
                        .weight(TEXT_MESSAGE_WEIGHT),
                    style = AppTypography.bodyMedium,
                    color = textColor,
                    fontWeight = FontWeight.W600
                )
                IconButton(
                    onClick = { snackbarHostState.currentSnackbarData?.dismiss() },
                    modifier = Modifier
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = CloseIconPaddingStart, end = CloseIconPaddingEnd)
                            .size(CloseIconSize),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close",
                        tint = textColor
                    )
                }
            }
        }
    }
}