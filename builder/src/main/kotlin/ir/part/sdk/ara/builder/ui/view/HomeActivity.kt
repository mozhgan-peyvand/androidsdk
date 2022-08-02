package ir.part.sdk.ara.builder.ui.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.base.di.MainScope
import ir.part.sdk.ara.builder.di.BuilderComponent
import ir.part.sdk.ara.builder.util.localizedContext
import ir.part.sdk.ara.common.ui.view.theme.AraTheme
import ir.part.sdk.ara.ui.menu.util.navigation.addMenuGraph
import ir.part.sdk.ara.ui.user.util.navigation.UserRouter
import ir.part.sdk.ara.ui.user.util.navigation.addUserGraph


@MainScope
class HomeActivity : ComponentProviderActivity() {

    companion object {
        var appId = ""
    }
    private var darkTheme = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideComponent().inject(this)

        setContent {
            LocalContext.current.applicationContext
            darkTheme = rememberSaveable {
                mutableStateOf(false)
            }

            AraTheme(darkTheme = darkTheme.value) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            color = MaterialTheme.colors.background
                        ),
                ) { innerPadding ->
                    AppNavigation()
                }

            }
        }
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        localizedContext(context)
        return super.onCreateView(name, context, attrs)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(localizedContext(base!!))
    }

    private fun provideComponent() = BuilderComponent.builder(this)

    @Composable
    fun AppNavigation(){
        val navController = rememberNavController()

        NavHost(navController, startDestination = UserRouter.Graph.router) {
            addUserGraph(navController)
            addMenuGraph(navController)
        }
    }
}
