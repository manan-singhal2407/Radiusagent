package com.radiusagent.assignment.presentation.theme.atom

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.radiusagent.assignment.R
import com.radiusagent.assignment.presentation.theme.AppTypography
import com.radiusagent.assignment.presentation.theme.PrimaryBackgroundColor
import com.radiusagent.assignment.presentation.theme.PrimaryTextColor
import com.radiusagent.assignment.presentation.theme.SecondaryTextColor

@SuppressLint("DiscouragedApi")
@Composable
fun RoundedChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val context = LocalContext.current
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(58.dp),
        contentPadding = PaddingValues(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (enabled) PrimaryTextColor else SecondaryTextColor
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) PrimaryTextColor else PrimaryBackgroundColor,
            contentColor = if (selected) {
                PrimaryBackgroundColor
            } else if (enabled) {
                PrimaryTextColor
            } else {
                SecondaryTextColor
            }
        ),
    ) {
        val resourceId = context.resources.getIdentifier(
            label.replace(Regex("[0-9]"), "").trim().replace(" ", "_").lowercase(),
            "drawable",
            context.packageName
        )
        val drawableRes = if (resourceId != 0) resourceId else R.drawable.apartment
        Icon(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = label,
            style = AppTypography.bodyLarge,
            maxLines = 1,
            fontWeight = FontWeight.W500
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    }
}