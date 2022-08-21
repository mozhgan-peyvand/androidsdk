package ir.part.sdk.ara.ui.document

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
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

@OptIn(ExperimentalUnitApi::class, ExperimentalPagerApi::class)
@Composable
fun DetailsScreenViewPager(
    documentSharedViewModel: DocumentSharedViewModel?,
    showResultValidation: Boolean = false
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
            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                Text(
                    text = stringResource(
                        id = stringRec.param_file_detail_title,
                        documentSharedViewModel?.itemPersonalDocument?.value?.fileId.toString()
                    ),
                    style = TextStyle(color = Color.Black),
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(
                        24F,
                        TextUnitType.Sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }) {

        Column() {
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
                        color = Color.Black
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
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


