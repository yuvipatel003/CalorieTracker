package com.appsdeviser.onboarding_presentation.screens.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.appsdeviser.core_ui.LocalSpacing
import com.appsdeviser.onboarding_presentation.R
import com.appsdeviser.onboarding_presentation.component.ActionButton

@Composable
fun WelcomeScreen(
    onNextClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ActionButton(
            text = stringResource(id = R.string.lets_go),
            onClick = { onNextClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
