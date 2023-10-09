package com.appsdeviser.tracker_presentation.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.appsdeviser.core_ui.LocalSpacing
import com.appsdeviser.tracker_domain.model.TrackableFood
import com.appsdeviser.core.R
import com.appsdeviser.tracker_presentation.components.NutrientInfo
import com.appsdeviser.tracker_presentation.search.TrackableFoodUiState
import kotlin.math.sin


@Composable
fun TrackableFoodItem(
    trackableFoodUiState: TrackableFoodUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onAmountChange: (amount: String) -> Unit,
    onTrack: () -> Unit
) {
    val trackableFood = trackableFoodUiState.trackableFood
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(end = spacing.spaceMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context).data(data = trackableFood.imageUrl)
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                                error(R.drawable.ic_burger)
                                fallback(R.drawable.ic_burger)
                            }).build()
                    ),
                    contentDescription = trackableFood.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 5.dp
                            )
                        )
                )
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                Column(
                    modifier = Modifier
                        .align(CenterVertically)
                ) {
                    Text(
                        text = trackableFood.name,
                        style = MaterialTheme.typography.body1,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
                    Text(
                        text = stringResource(
                            id = R.string.kcal_per_100g,
                            trackableFood.caloriesPer100g
                        ),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackableFood.carbsPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackableFood.proteinPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2
                )
                Spacer(modifier = modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackableFood.fatPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameTextStyle = MaterialTheme.typography.body2
                )
            }
        }
        AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spacing.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row {
                    BasicTextField(
                        value = trackableFoodUiState.amount,
                        onValueChange = {
                            onAmountChange(it)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = if (trackableFoodUiState.amount.isNotBlank()) {
                                ImeAction.Done
                            } else ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onTrack()
                                defaultKeyboardAction(ImeAction.Done)
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 0.5.dp,
                                color = MaterialTheme.colors.onSurface
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.spaceMedium)
                    )

                    Spacer(
                        modifier = Modifier.width(spacing.spaceExtraSmall)
                    )
                    Text(
                        text = stringResource(id = R.string.grams),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.alignBy(LastBaseline)
                    )
                }
                IconButton(
                    onClick = onTrack,
                    enabled = trackableFoodUiState.amount.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.track)
                    )
                }

            }

        }
    }

}
