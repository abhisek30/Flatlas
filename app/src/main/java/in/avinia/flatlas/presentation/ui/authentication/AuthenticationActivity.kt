package `in`.avinia.flatlas.presentation.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import `in`.avinia.flatlas.presentation.theme.FlatlasTheme
import `in`.avinia.flatlas.presentation.ui.authentication.composable.AuthenticationScreen
import `in`.avinia.flatlas.presentation.ui.dashboard.DashboardActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<AuthenticationViewModel>()
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
            FlatlasTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                ) { innerPadding ->
                    AuthenticationScreen(modifier = Modifier.padding(innerPadding),
                        action = { action ->
                            viewModel.handleAction(action)
                        })
                }
            }

            LaunchedEffect(Unit) {
                viewModel.uiEffect.onEach { effect ->
                    when (effect) {
                        is AuthenticationEffect.NavigateToDashboard -> {
                            startActivity(
                                Intent(
                                    this@AuthenticationActivity,
                                    DashboardActivity::class.java
                                )
                            )
                            finish()
                        }

                        is AuthenticationEffect.ShowError -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = effect.message,
                                )
                            }
                        }
                    }
                }.collect()
            }
        }
    }
}