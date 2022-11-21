package ir.part.sdk.ara.ui.menu.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ir.part.sdk.ara.common.ui.view.common.HighlightedBulletWithTextBody2Secondary
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.divider
import ir.part.sdk.ara.common.ui.view.theme.body2BoldTextPrimary
import ir.part.sdk.ara.common.ui.view.theme.body2BoldTextSecondary
import ir.part.sdk.ara.common.ui.view.theme.h5PrimaryVariant
import ir.part.sdk.ara.ui.menu.R

@Composable
fun GuideScreen(onNavigateUp: () -> Unit) {
    Column() {
        TopAppBar(
            backgroundColor = Color.White,
            elevation = dimensionResource(id = R.dimen.spacing_half_base)
        ) {
            TopAppBarContent(title = stringResource(id = R.string.ara_label_guide),
                onNavigateUp = {
                    onNavigateUp()
                })
        }
        GuideContent()
    }
}

@Composable
private fun GuideContent() {
    val context = LocalContext.current

    var guideViewList: List<GuideView> by remember {
        mutableStateOf(listOf())
    }

    LaunchedEffect(key1 = true, block = {
        if (guideViewList.isEmpty()) {
            guideViewList = getListOfGuideView(context)
        }
    })


    LazyColumn(
        Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.spacing_4x),
        ), horizontalAlignment = Alignment.Start
    ) {
        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_7x)))
            Row(
                modifier = Modifier
                    .clickable {
                        downloadGuideFile(context)
                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ara_ic_download),
                    tint = Color.Unspecified,
                    contentDescription = "download"
                )

                Text(
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_3x)),
                    text = stringResource(id = R.string.ara_label_download_guide_file),
                    style = MaterialTheme.typography.h5PrimaryVariant()
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4x)))
        }

        itemsIndexed(guideViewList,
            key = { _, item ->
                item.step
            }
        ) { _, item ->
            GuideItemExpandable(item)
        }

        item {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_one))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_two))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_three))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_four))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_five))

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))
            HighlightedBulletWithTextBody2Secondary(text = stringResource(id = R.string.ara_description_array_guide_six))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_3x)))

        }
    }
}

@Composable
private fun GuideItemExpandable(item: GuideView) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.spacing_base)),
        elevation = dimensionResource(id = R.dimen.spacing_half_base),
        shape = MaterialTheme.shapes.medium
    ) {
        var detailsVisibility by remember {
            mutableStateOf(false)
        }

        var rotateAngleState by remember {
            mutableStateOf(0f)
        }
        val rotateAngle by animateFloatAsState(targetValue = rotateAngleState)

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(modifier = Modifier
                .background(color = MaterialTheme.colors.divider())
                .clickable(enabled = item.description.isNotEmpty()) {
                    detailsVisibility = detailsVisibility.not()
                    rotateAngleState = if (detailsVisibility) {
                        180f
                    } else {
                        0f
                    }
                }
                .padding(
                    horizontal = dimensionResource(id = R.dimen.spacing_4x),
                    vertical = dimensionResource(id = R.dimen.spacing_3x)
                )
                .fillMaxWidth(),
                horizontalArrangement = if (item.description.isEmpty()) Arrangement.Start else Arrangement.SpaceBetween) {
                Text(
                    textAlign = TextAlign.Start,
                    text = item.step,
                    style = MaterialTheme.typography.body2BoldTextSecondary(),
                )

                if (item.description.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ara_ic_arrow_down),
                        contentDescription = "expand", modifier = Modifier
                            .rotate(rotateAngle)
                    )
                }

            }

            Text(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_2x)),
                text = item.title,
                style = MaterialTheme.typography.body2BoldTextPrimary(),
            )

            AnimatedVisibility(visible = detailsVisibility) {
                Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_2x))) {
                    for (desc in item.description) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2x)))
                        HighlightedBulletWithTextBody2Secondary(text = desc)
                    }
                }
            }
        }
    }
}

private fun getListOfGuideView(context: Context): List<GuideView> {
    val steps = context.resources.getStringArray(R.array.ara_step_array)
    val titles = context.resources.getStringArray(R.array.ara_title_array_guide)

    val one = context.resources.getStringArray(R.array.ara_details_array_guide_one)
    val two = context.resources.getStringArray(R.array.ara_details_array_guide_two)
    val three = context.resources.getStringArray(R.array.ara_details_array_guide_three)
    val four = context.resources.getStringArray(R.array.ara_details_array_guide_four)
    val five = context.resources.getStringArray(R.array.ara_details_array_guide_five)
    val six = context.resources.getStringArray(R.array.ara_details_array_guide_six)
    val seven = context.resources.getStringArray(R.array.ara_details_array_guide_seven)
    val eight = context.resources.getStringArray(R.array.ara_details_array_guide_eight)
    val nine = context.resources.getStringArray(R.array.ara_details_array_guide_nine)
    val ten = context.resources.getStringArray(R.array.ara_details_array_guide_ten)
    val eleven = context.resources.getStringArray(R.array.ara_details_array_guide_eleven)
    val twelve = context.resources.getStringArray(R.array.ara_details_array_guide_twelve)
    val thirteen = context.resources.getStringArray(R.array.ara_details_array_guide_thirteen)
    val fourteen = context.resources.getStringArray(R.array.ara_details_array_guide_fourteen)

    return listOf(
        GuideView(title = titles[0], step = steps[0], one.toList()),
        GuideView(title = titles[1], step = steps[1], two.toList()),
        GuideView(title = titles[2], step = steps[2], three.toList()),
        GuideView(title = titles[3], step = steps[3], four.toList()),
        GuideView(title = titles[4], step = steps[4], five.toList()),
        GuideView(title = titles[5], step = steps[5], six.toList()),
        GuideView(title = titles[6], step = steps[6], seven.toList()),
        GuideView(title = titles[7], step = steps[7], eight.toList()),
        GuideView(title = titles[8], step = steps[8], nine.toList()),
        GuideView(title = titles[9], step = steps[9], ten.toList()),
        GuideView(title = titles[10], step = steps[10], eleven.toList()),
        GuideView(title = titles[11], step = steps[11], twelve.toList()),
        GuideView(title = titles[12], step = steps[12], thirteen.toList()),
        GuideView(title = titles[13], step = steps[13], fourteen.toList()),
    )
}

private fun downloadGuideFile(context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(context.getString(R.string.ara_label_download_guide_file_url))
    )
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    context.startActivity(intent)
}