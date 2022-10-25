package ir.part.sdk.ara.ui.document.detailDocument

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import ir.part.sdk.ara.common.ui.view.common.TopAppBarContent
import ir.part.sdk.ara.common.ui.view.textPrimary
import ir.part.sdk.ara.ui.document.R
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import kotlinx.coroutines.launch


sealed class TabItem(
    val title: Int,
    val screenToLoad: @Composable (viewModel: DocumentSharedViewModel?) -> Unit
) {
    object DocumentStatus :
        TabItem(title = stringRec.label_files_messages_list_title, screenToLoad = {
            DocumentStatusScreen(it)
        })

    object DocumentDetails :
        TabItem(title = stringRec.label_file_detail_request_title, screenToLoad = {
            it?.let { it1 -> DocumentDetailsScreen(it1) }
        })

    object ValidationResult :
        TabItem(title = stringRec.label_file_validation_result, screenToLoad = {
            ValidationResultScreen(it)
        })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailsScreenViewPager(
    documentSharedViewModel: DocumentSharedViewModel?,
    showResultValidation: Boolean = false,
    onNavigateUp: () -> Unit
) {

    val list =
        if (documentSharedViewModel?.itemPersonalDocument?.value?.showValidationProperties == false) {
            listOf(
                TabItem.DocumentStatus,
                TabItem.DocumentDetails,
                TabItem.ValidationResult
            )
        } else {
            listOf(
                TabItem.DocumentStatus,
                TabItem.DocumentDetails
            )
        }

    val pagerState = if (!showResultValidation) {
        rememberPagerState()
    } else {
        rememberPagerState(initialPage = list.lastIndex)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = dimensionResource(id = R.dimen.spacing_half_base)
            ) {
                TopAppBarContent(title = stringResource(
                    id = stringRec.param_file_detail_title,
                    documentSharedViewModel?.itemPersonalDocument?.value?.fileId.toString()
                ),
                    onNavigateUp = {
                        onNavigateUp()
                    })
            }
        }) {
        Column(Modifier.padding(it)) {
            Tabs(pagerState = pagerState, list = list)

            if (documentSharedViewModel != null) {
                TabsContent(
                    pagerState = pagerState,
                    tabItems = list,
                    viewModel = documentSharedViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Tabs(pagerState: PagerState, list: List<TabItem>) {
    val scope = rememberCoroutineScope()
    TabRow(

        selectedTabIndex = pagerState.currentPage,

        backgroundColor = Color.White,

        contentColor = Color.Black,

        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = colorResource(id = R.color.commonResourceColorPrimaryVariant)
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        stringResource(id = list[index].title),
                        style = MaterialTheme.typography.subtitle2
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                selectedContentColor = MaterialTheme.colors.textPrimary()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabsContent(
    pagerState: PagerState,
    tabItems: List<TabItem>,
    viewModel: DocumentSharedViewModel
) {
    HorizontalPager(state = pagerState, count = tabItems.size) { page ->
        when (page) {
            0 -> tabItems[page].screenToLoad(viewModel)
            1 -> tabItems[page].screenToLoad(viewModel)
            2 -> tabItems[page].screenToLoad(viewModel)
        }

    }
}


