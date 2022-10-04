package viach.apps.ai.ai

import kotlinx.parcelize.Parcelize
import viach.apps.dicing.game.Game
import kotlin.random.Random

@Parcelize
class TwoPlayersEasyAI(
    override val game: Game,
    private val ownPlayerPosition: Int,
    private val opponentPlayerPosition: Int
) : AI {

    override tailrec fun makeMove(): AI {
        val field = game.getGameField(ownPlayerPosition)
        if (field.gameOver) return this
        val fieldPosition = Random.nextInt(1, 9)
        if (field.isFree(fieldPosition)) {
            return TwoPlayersEasyAI(
                game = game.makeMove(fieldPosition, ownPlayerPosition),
                ownPlayerPosition = ownPlayerPosition,
                opponentPlayerPosition = opponentPlayerPosition
            )
        }
        return makeMove()
    }

    override fun updateGame(game: Game): AI =
        TwoPlayersEasyAI(game, ownPlayerPosition, opponentPlayerPosition)
}