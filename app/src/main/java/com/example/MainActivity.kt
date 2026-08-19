package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.CertificateDialog
import com.example.ui.components.KepBottomNavigationBar
import com.example.ui.screens.ChapterReadingScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LaborToolsScreen
import com.example.ui.screens.ModuleDetailScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LearningViewModel
import com.example.ui.viewmodel.ScreenNav

class MainActivity : ComponentActivity() {
    private val viewModel: LearningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: LearningViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    var activeCertificateModuleId by remember { mutableStateOf<String?>(null) }

    // Intercept back button if not on Home
    BackHandler(enabled = currentScreen !is ScreenNav.Home) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Show bottom bar for top-level screens
            val showBottomNav = currentScreen is ScreenNav.Home ||
                    currentScreen is ScreenNav.LaborTools ||
                    currentScreen is ScreenNav.Dictionary ||
                    currentScreen is ScreenNav.Profile

            if (showBottomNav) {
                KepBottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavigate = { destination ->
                        viewModel.navigateTo(destination)
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val screen = currentScreen) {
                is ScreenNav.Home -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToModule = { moduleId ->
                            viewModel.navigateTo(ScreenNav.ModuleDetail(moduleId))
                        },
                        onNavigateToTools = {
                            viewModel.navigateTo(ScreenNav.LaborTools)
                        },
                        onNavigateToDictionary = {
                            viewModel.navigateTo(ScreenNav.Dictionary)
                        },
                        onNavigateToProfile = {
                            viewModel.navigateTo(ScreenNav.Profile)
                        }
                    )
                }

                is ScreenNav.ModuleDetail -> {
                    ModuleDetailScreen(
                        moduleId = screen.moduleId,
                        viewModel = viewModel,
                        onBackClick = { viewModel.navigateBack() },
                        onChapterClick = { chapterId ->
                            viewModel.navigateTo(ScreenNav.ChapterRead(chapterId))
                        },
                        onQuizClick = { modId ->
                            viewModel.navigateTo(ScreenNav.Quiz(modId))
                        }
                    )
                }

                is ScreenNav.ChapterRead -> {
                    ChapterReadingScreen(
                        chapterId = screen.chapterId,
                        viewModel = viewModel,
                        onBackClick = { viewModel.navigateBack() },
                        onNavigateToChapter = { targetChapterId ->
                            viewModel.navigateTo(ScreenNav.ChapterRead(targetChapterId))
                        }
                    )
                }

                is ScreenNav.Quiz -> {
                    QuizScreen(
                        moduleId = screen.moduleId,
                        viewModel = viewModel,
                        onBackClick = { viewModel.navigateBack() },
                        onShowCertificate = { modId ->
                            activeCertificateModuleId = modId
                        }
                    )
                }

                is ScreenNav.LaborTools -> {
                    LaborToolsScreen(viewModel = viewModel)
                }

                is ScreenNav.Dictionary -> {
                    DictionaryScreen(viewModel = viewModel)
                }

                is ScreenNav.Profile -> {
                    ProfileScreen(
                        viewModel = viewModel,
                        onNavigateToModule = { moduleId ->
                            viewModel.navigateTo(ScreenNav.ModuleDetail(moduleId))
                        },
                        onNavigateToChapter = { chapterId ->
                            viewModel.navigateTo(ScreenNav.ChapterRead(chapterId))
                        }
                    )
                }
            }

            // E-Certificate Dialog
            if (activeCertificateModuleId != null) {
                val mod = viewModel.getModule(activeCertificateModuleId!!)
                CertificateDialog(
                    userName = userProfile.name,
                    pukName = userProfile.pukName,
                    moduleTitle = mod?.title ?: "Modul SP KEP SPSI",
                    memberNumber = userProfile.memberNumber,
                    scorePercentage = 100,
                    onDismiss = { activeCertificateModuleId = null }
                )
            }
        }
    }
}

