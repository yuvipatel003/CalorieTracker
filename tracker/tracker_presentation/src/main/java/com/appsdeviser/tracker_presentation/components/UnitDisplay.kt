package com.appsdeviser.tracker_presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appsdeviser.core_ui.Dimensions
import com.appsdeviser.core_ui.LocalSpacing

@Composable
fun UnitDisplay(
    amount:Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountTextColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitTextColor: Color = MaterialTheme.colors.onBackground
){
    val spacing = LocalSpacing.current

    Row(modifier = modifier){
        Text(
            text = amount.toString(),
            style = MaterialTheme.typography.h1,
            color = amountTextColor,
            fontSize = amountTextSize,
            modifier = modifier.alignBy(LastBaseline)
        )
        Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
        Text(
            text = unit,
            style = MaterialTheme.typography.body1,
            color = unitTextColor,
            fontSize = unitTextSize,
            modifier = modifier.alignBy(LastBaseline)
        )
    }
}