package viach.apps.dicing.ui.view.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import org.koin.androidx.compose.get
import viach.apps.ai.ai.AI
import viach.apps.cache.settings.SavedGameCache
import viach.apps.cache.settings.SettingsCache
import viach.apps.cache.status.StatsCache
import viach.apps.dicing.di.createGameFromSavedData
import viach.apps.dicing.game.Game
import viach.apps.dicing.model.AIDifficulty
import viach.apps.dicing.model.GameType
import viach.apps.dicing.model.Screen

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    userVsUserGameFactory: @Composable () -> Game = { get(GameType.USER_VS_USER.qualifier) },
    userVsAiGame: Game = get(GameType.USER_VS_AI.qualifier),
    easyAI: AI = get(AIDifficulty.EASY.qualifier),
    normalAI: AI = get(AIDifficulty.NORMAL.qualifier),
    hardAI: AI = get(AIDifficulty.HARD.qualifier),
    stats: StatsCache = get(),
    settingsCache: SettingsCache = get(),
    savedGameCache: SavedGameCache = get()
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberAnimatedNavController(bottomSheetNavigator)
    ModalBottomSheetLayout(
        bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    ) {
        AnimatedNavHost(navController = navController, startDestination = Screen.Menu.route) {
            composable(Screen.Menu.route) {
                MenuScreen(
                    savedGameCache = savedGameCache,
                    settingsCache = settingsCache,
                    onPlayOpenIntent = { difficulty ->
                        when (difficulty) {
                            AIDifficulty.EASY -> navController.navigate(Screen.PlayEasyGame.route)
                            AIDifficulty.NORMAL -> navController.navigate(Screen.PlayNormalGame.route)
                            AIDifficulty.HARD -> navController.navigate(Screen.PlayHardGame.route)
                        }
                    },
                    onContinueGameOpenIntent = { navController.navigate(Screen.SavedGame.route) },
                    onTwoPlayersOpenIntent = { navController.navigate(Screen.TwoPlayersGame.route) },
                    onStatsOpenIntent = { navController.navigate(Screen.Stats.route) },
                    onRulesOpenIntent = { navController.navigate(Screen.Rules.route) },
                    onSettingsOpenIntent = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(
                Screen.PlayEasyGame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) }
            ) {
                GameScreen(
                    game = userVsAiGame,
                    stats = stats,
                    savedGameCache = savedGameCache,
                    ai = easyAI,
                    difficulty = AIDifficulty.EASY,
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
            composable(
                Screen.PlayNormalGame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) }
            ) {
                GameScreen(
                    game = userVsAiGame,
                    stats = stats,
                    savedGameCache = savedGameCache,
                    ai = normalAI,
                    difficulty = AIDifficulty.NORMAL,
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
            composable(
                Screen.PlayHardGame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) }
            ) {
                GameScreen(
                    game = userVsAiGame,
                    stats = stats,
                    savedGameCache = savedGameCache,
                    ai = hardAI,
                    difficulty = AIDifficulty.HARD,
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
            composable(
                Screen.TwoPlayersGame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) }
            ) {
                GameScreen(
                    game = userVsUserGameFactory(),
                    stats = stats,
                    savedGameCache = savedGameCache,
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
            composable(
                Screen.SavedGame.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Start) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.End) }
            ) {
                GameScreen(
                    game = remember(savedGameCache) {
                        createGameFromSavedData(
                            savedGameCache.turn,
                            savedGameCache.playerOneField,
                            savedGameCache.playerTwoField,
                            savedGameCache.nextDice
                        )
                    },
                    ai = remember(savedGameCache.difficulty) {
                        when (savedGameCache.difficulty) {
                            AIDifficulty.EASY.name -> easyAI
                            AIDifficulty.NORMAL.name -> normalAI
                            AIDifficulty.HARD.name -> hardAI
                            else -> easyAI
                        }
                    },
                    difficulty = remember(savedGameCache.difficulty) {
                        AIDifficulty.values().find { it.name == savedGameCache.difficulty } ?: AIDifficulty.EASY
                    },
                    stats = stats,
                    savedGameCache = savedGameCache,
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
            bottomSheet(Screen.Stats.route) {
                StatsScreen(stats = stats)
            }
            bottomSheet(Screen.Settings.route) {
                SettingsScreen(settingsCache = settingsCache)
            }
            composable(
                Screen.Rules.route,
                enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
                exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) },
                popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Up) },
                popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Down) }
            ) {
                RulesScreen(
                    game = userVsUserGameFactory(),
                    onBackToMenuIntent = { navController.popBackStack() }
                )
            }
        }
    }
}