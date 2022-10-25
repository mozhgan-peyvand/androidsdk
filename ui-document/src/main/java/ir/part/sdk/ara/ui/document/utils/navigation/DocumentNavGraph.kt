package ir.part.sdk.ara.ui.document.utils.navigation

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.ui.document.detailDocument.DetailsScreenViewPager
import ir.part.sdk.ara.ui.document.di.DocumentComponent
import ir.part.sdk.ara.ui.document.overviewDocument.DocumentSharedViewModel
import ir.part.sdk.ara.ui.document.overviewDocument.FileListScreen
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentScreen
import ir.part.sdk.ara.ui.document.submitDocument.SubmitDocumentViewModel
import ir.part.sdk.ara.ui.shared.feature.screens.task.TasksManagerViewModel


fun NavGraphBuilder.addDocumentGraph(
    navController: NavHostController,
    tasksManagerViewModel: TasksManagerViewModel
) {
    navigation(
        route = DocumentRouter.DocumentGraph.router,
        startDestination = DocumentRouter.DocumentFileListScreen.router
    ) {

        submitScreen {

            val submitDocumentViewModel: SubmitDocumentViewModel = viewModel(
                factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .providerSubmitDocumentViewModel(),
                viewModelStoreOwner = it
            )

            SubmitDocumentScreen(
                viewModel = submitDocumentViewModel,
                navigateToFileList = { navController.navigateToFileListScreen() },
                tasksManagerViewModel = tasksManagerViewModel
            )
        }

        fileListScreen {
            val docBackStackEntry = remember {
                navController.getBackStackEntry(DocumentRouter.DocumentGraph.router)
            }

            val sharedViewModel: DocumentSharedViewModel = viewModel(
                factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .providerDocumentSharedViewModel(),
                viewModelStoreOwner = docBackStackEntry

            )

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

            val docBackStackEntry = remember {
                navController.getBackStackEntry(DocumentRouter.DocumentGraph.router)
            }

            val sharedViewModel: DocumentSharedViewModel = viewModel(
                factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .providerDocumentSharedViewModel(),
                viewModelStoreOwner = docBackStackEntry

            )

            DetailsScreenViewPager(
                documentSharedViewModel = sharedViewModel,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }


        resultValidationScreen {

            val docBackStackEntry = remember {
                navController.getBackStackEntry(DocumentRouter.DocumentGraph.router)
            }

            val sharedViewModel: DocumentSharedViewModel = viewModel(
                factory = DocumentComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .providerDocumentSharedViewModel(),
                viewModelStoreOwner = docBackStackEntry

            )

            DetailsScreenViewPager(
                documentSharedViewModel = sharedViewModel,
                showResultValidation = true,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}
