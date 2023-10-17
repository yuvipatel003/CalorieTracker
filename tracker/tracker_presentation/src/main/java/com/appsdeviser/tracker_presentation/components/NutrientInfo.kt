package com.appsdeviser.tracker_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appsdeviser.core_ui.Dimensions
import com.appsdeviser.core_ui.LocalSpacing

@Composable
fun NutrientInfo(
    name: String,
    amount: Int,
    unit: String,
    modifier: Modifier = Modifier,
    amountTextSize: TextUnit = 20.sp,
    amountTextColor: Color = MaterialTheme.colors.onBackground,
    unitTextSize: TextUnit = 14.sp,
    unitTextColor: Color = MaterialTheme.colors.onBackground,
    nameTextStyle: TextStyle = MaterialTheme.typography.body1
){
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UnitDisplay(
            amount = amount,
            unit = unit,
            modifier = modifier,
            amountTextSize = amountTextSize,
            amountTextColor = amountTextColor,
            unitTextSize = unitTextSize,
            unitTextColor = unitTextColor
        )
        Spacer(modifier = modifier.height(spacing.spaceExtraSmall))
        Text(
            text = name,
            style = nameTextStyle,
            fontWeight = FontWeight.Light
        )
    }
}