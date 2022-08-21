package ir.part.sdk.ara.ui.document.utils.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.ui.document.DetailsScreenViewPager
import ir.part.sdk.ara.ui.document.di.DocumentComponent
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.overviewDocument.FileListScreen
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentScreen
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentViewModel


fun NavGraphBuilder.addDocumentGraph(navController: NavHostController) {
    navigation(
        route = DocumentRouter.DocumentGraph.router,
        startDestination = DocumentRouter.DocumentFileListScreen.router
    ) {

        var sharedViewModel: DocumentSharedViewModel? = null

        submitScreen {
            val submitDocumentViewModel: SubmitDocumentViewModel = viewModel(
                factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .providerSubmitDocumentViewModel(),
                viewModelStoreOwner = LocalContext.current as ComponentProviderActivity

            )

            SubmitDocumentScreen(
                viewModel = submitDocumentViewModel,
                navigateToFileList = { navController.navigateToFileListScreen() }
            )
        }

        fileListScreen {

            if (sharedViewModel == null) {
                sharedViewModel = viewModel(
                    factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                        .providerDocumentSharedViewModel(),
                    viewModelStoreOwner = LocalContext.current as ComponentProviderActivity

                )
            }

            FileListScreen(
                viewModel = sharedViewModel,
                navigateToDetail = {
                    navController.navigateToDetail()
                },
                navigateToValidationResult = {
                    navController.navigateToResultValidation()
                }
            )
        }

        fileDetailScreenViewPager {

            DetailsScreenViewPager(
                documentSharedViewModel = sharedViewModel
            )
        }


        resultValidationScreen {

            DetailsScreenViewPager(
                documentSharedViewModel = sharedViewModel,
                showResultValidation = true
            )

        }
    }
}
