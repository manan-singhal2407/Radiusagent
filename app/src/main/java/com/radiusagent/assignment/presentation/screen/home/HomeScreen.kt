package com.radiusagent.assignment.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.radiusagent.assignment.R
import com.radiusagent.assignment.presentation.theme.AppTypography
import com.radiusagent.assignment.presentation.theme.PrimaryBackgroundColor
import com.radiusagent.assignment.presentation.theme.PrimaryTextColor
import com.radiusagent.assignment.presentation.theme.atom.RoundedChip
import com.radiusagent.assignment.presentation.theme.atom.SnackBar

//region Constants
private val SpaceFromParentTop = 20.dp
private val PaddingBetweenTextAndChip = 20.dp
private val SpaceBetweenChipAndParentStart = 20.dp
private val SpaceBetweenChips = 12.dp
private val SpaceBetweenChipAndParentEnd = 8.dp
//endregion

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    if (viewModel.facilityCache == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(id = R.string.loading_text),
                style = AppTypography.bodyLarge,
                color = PrimaryTextColor,
                fontWeight = FontWeight.W600
            )
        }
    }

    viewModel.facilityCache?.let { facilityCache ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PrimaryBackgroundColor)
        ) {
            Spacer(modifier = Modifier.height(SpaceFromParentTop))
            facilityCache.facilities.forEach { facility ->
                Text(
                    facility.name,
                    modifier = Modifier.padding(PaddingBetweenTextAndChip),
                    style = AppTypography.titleLarge,
                    color = PrimaryTextColor,
                    fontWeight = FontWeight.W600
                )
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.width(SpaceBetweenChipAndParentStart))
                    facility.option.forEach { option ->
                        RoundedChip(
                            label = option.name,
                            selected = viewModel.selectedPropertyType.contains(option.id),
                            onClick = { viewModel.onChipClick(option.id, option.disabled) },
                            enabled = viewModel.checkIfChipIsEnabled(option.disabled),
                        )
                        Spacer(modifier = Modifier.width(SpaceBetweenChips))
                    }
                    Spacer(modifier = Modifier.width(SpaceBetweenChipAndParentEnd))
                }
            }
        }
    }

    if (viewModel.snackbarMessage.isNotEmpty()) {
        val snackbarHostState = remember { SnackbarHostState() }
        SnackBar(
            modifier = Modifier.fillMaxSize(),
            snackbarHostState = snackbarHostState
        )
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(viewModel.snackbarMessage)
            viewModel.snackbarMessage = ""
        }
    }
}