package viach.apps.dicing.ui.view.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import viach.apps.ai.ai.AI
import viach.apps.cache.status.StatsCache
import viach.apps.dicing.R
import viach.apps.dicing.game.Game
import viach.apps.dicing.model.AIDifficulty
import viach.apps.dicing.ui.view.component.*

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("SwitchIntDef")
@Suppress("NAME_SHADOWING")
@Composable
fun GameScreen(
    game: Game,
    stats: StatsCache,
    ai: AI? = null,
    difficulty: AIDifficulty? = null,
    onBackToMenuIntent: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val orientation = LocalConfiguration.current.orientation
    var game: Game by rememberSaveable { mutableStateOf(game) }
    var ai: AI? by rememberSaveable { mutableStateOf(ai) }
    var message by rememberSaveable {
        val playerPosition = if (game.isPlayerMove(1)) 1 else 2
        val message = context.getString(R.string.player_format_moves, playerPosition)
        mutableStateOf<String?>(message)
    }
    var scoreNotUpdated by rememberSaveable { mutableStateOf(difficulty != null) }

    message?.let {
        MessageDialog(
            message = it,
            onDismissIntent = { message = null }
        )
    }

    var leaving by remember { mutableStateOf(false) }

    BackHandler(true) { leaving = true }
    CustomDialog(
        isVisible = leaving,
        title = stringResource(R.string.leaving),
        message = stringResource(R.string.leaving_prompt),
        confirmButton = {
            MaxWidthButton(
                text = stringResource(R.string.yes),
                onClick = {
                    leaving = false
                    onBackToMenuIntent()
                }
            )
        },
        onDismissIntent = { leaving = false }
    )

    LaunchedEffect(game.gameOver, scoreNotUpdated) {
        if (game.gameOver) {
            if (scoreNotUpdated) {
                when (difficulty) {
                    AIDifficulty.EASY -> {
                        stats.setEasyModeHighScore(game.getGameField(1).score)
                        stats.addEasyWinLossPoint(game.wonPlayerPosition == 1)
                    }
                    AIDifficulty.NORMAL -> {
                        stats.setNormalModeHighScore(game.getGameField(1).score)
                        stats.addNormalWinLossPoint(game.wonPlayerPosition == 1)
                    }
                    AIDifficulty.HARD -> {
                        stats.setHardModeHighScore(game.getGameField(1).score)
                        stats.addHardWinLossPoint(game.wonPlayerPosition == 1)
                    }
                    null -> {}
                }
                scaffoldState.bottomSheetState.expand()
                scoreNotUpdated = false
            }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = animateDpAsState(if (game.gameOver) BottomSheetScaffoldDefaults.SheetPeekHeight else 0.dp).value,
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetContent = {
            VerticalSpacer(16.dp)
            Text(
                stringResource(id = R.string.game_over),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier
                        .background(MaterialTheme.colors.onBackground, RoundedCornerShape(4.dp))
                        .size(width = 100.dp, height = 8.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (game.gameOver) {
                    Text(
                        text = stringResource(R.string.player_format_won_the_game, game.wonPlayerPosition!!),
                        modifier = Modifier.padding(32.dp),
                        style = MaterialTheme.typography.body2
                    )
                    MaxWidthButton(
                        textRes = R.string.play_again,
                        onClick = { game = game.newGame }
                    )
                    VerticalSpacer(16.dp)
                    MaxWidthButton(
                        textRes = R.string.back_to_menu,
                        onClick = onBackToMenuIntent
                    )
                    VerticalSpacer(16.dp)

                    var showBoard by remember { mutableStateOf(false) }

                    MaxWidthButton(
                        textRes = R.string.show_hide_board,
                        onClick = { showBoard = !showBoard }
                    )

                    AnimatedVisibility(visible = showBoard) {
                        val player2 = game.getGameField(2)
                        val player1 = game.getGameField(1)

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                stringResource(R.string.player_location, 2),
                                style = MaterialTheme.typography.h4
                            )
                            for (y in 0 until 3) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (x in 0 until 3) {
                                        val position = y * 3 + x + 1
                                        val dice = player2.getDice(position)
                                        Text(dice.value.toString())
                                    }
                                }
                            }

                            Divider()

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "${player1.score}",
                                    style = MaterialTheme.typography.body1
                                )
                                Text(
                                    "${player2.score}",
                                    style = MaterialTheme.typography.body1
                                )
                            }

                            Divider()

                            for (y in 0 until 3) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (x in 0 until 3) {
                                        val position = y * 3 + x + 1
                                        val dice = player1.getDice(position)
                                        Text(dice.value.toString())
                                    }
                                }
                            }
                            Text(
                                stringResource(R.string.player_location, 1),
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                PortraitScreen(
                    context = context,
                    coroutineScope = coroutineScope,
                    scrollState = scrollState,
                    game = game,
                    ai = ai,
                    onGameChange = { game = it },
                    onAIChange = { ai = it },
                    onMessageChange = { message = it },
                    paddingValues = padding
                )
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                LandscapeScreen(
                    context = context,
                    coroutineScope = coroutineScope,
                    scrollState = scrollState,
                    game = game,
                    ai = ai,
                    onAIChange = { ai = it },
                    onGameChange = { game = it },
                    onMessageChange = { message = it },
                    paddingValues = padding
                )
            }
        }
    }
}

@Composable
private fun PortraitScreen(
    context: Context,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState,
    game: Game,
    ai: AI?,
    paddingValues: PaddingValues,
    onGameChange: (Game) -> Unit,
    onAIChange: (AI) -> Unit,
    onMessageChange: (String?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameBar(
            gameField = game.getGameField(2),
            rowCellsCount = 3,
            playerPosition = 2,
            layoutPadding = PaddingValues(16.dp),
            itemPadding = PaddingValues(8.dp),
            itemsClickable = ai == null || !game.gameOver
        ) { position ->
            if (game.isPlayerMove(2)) {
                onGameChange(game.makeMove(position, 2).createNextDice())
            } else {
                onMessageChange(context.getString(R.string.player_format_moves, 1))
            }
        }
        StatusBar(
            leftScore = game.getGameField(1).score,
            rightScore = game.getGameField(2).score,
            dice = game.nextDice
        )
        GameBar(
            gameField = game.getGameField(1),
            rowCellsCount = 3,
            layoutPadding = PaddingValues(16.dp),
            itemPadding = PaddingValues(8.dp),
            playerPosition = 1,
            itemsClickable = !game.gameOver
        ) { position ->
            if (game.isPlayerMove(1)) {
                val newGame = game.makeMove(position, 1).createNextDice()
                onGameChange(newGame)
                if (ai != null && !newGame.gameOver) {
                    coroutineScope.launch {
                        delay(1000)
                        val newAI = ai.updateGame(newGame).makeMove()
                        onAIChange(newAI)
                        onGameChange(newAI.game.createNextDice())
                    }
                }
            } else {
                onMessageChange(context.getString(R.string.player_format_moves, 2))
            }
        }
    }
}

@Composable
private fun LandscapeScreen(
    context: Context,
    coroutineScope: CoroutineScope,
    scrollState: ScrollState,
    game: Game,
    ai: AI?,
    onGameChange: (Game) -> Unit,
    onAIChange: (AI) -> Unit,
    onMessageChange: (String?) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .rotate(90f)
            ) {
                GameBar(
                    gameField = game.getGameField(1),
                    rowCellsCount = 3,
                    layoutPadding = PaddingValues(16.dp),
                    itemPadding = PaddingValues(8.dp),
                ) { position ->
                    if (game.isPlayerMove(1)) {
                        val newGame = game.makeMove(position, 1).createNextDice()
                        onGameChange(newGame)
                        if (ai != null && !newGame.gameOver) {
                            coroutineScope.launch {
                                delay(1000)
                                val newAI = ai.updateGame(newGame).makeMove()
                                onAIChange(newAI)
                                onGameChange(newAI.game.createNextDice())
                            }
                        }
                    } else {
                        onMessageChange(context.getString(R.string.player_format_moves, 2))
                    }
                }
            }
            HorizontalSpacer(16.dp)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .rotate(90f)
            ) {
                GameBar(
                    gameField = game.getGameField(2),
                    rowCellsCount = 3,
                    layoutPadding = PaddingValues(16.dp),
                    itemPadding = PaddingValues(8.dp),
                    itemsClickable = ai == null
                ) { position ->
                    if (game.isPlayerMove(2)) {
                        onGameChange(game.makeMove(position, 2).createNextDice())
                    } else {
                        onMessageChange(context.getString(R.string.player_format_moves, 1))
                    }
                }
            }
        }
        StatusBar(
            leftScore = game.getGameField(1).score,
            rightScore = game.getGameField(2).score,
            dice = game.nextDice
        )
    }
}