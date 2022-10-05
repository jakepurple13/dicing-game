package viach.apps.dicing.dicefactory

import kotlinx.parcelize.Parcelize
import viach.apps.dicing.dice.*
import kotlin.random.Random

@Parcelize
object RandomDiceFactory : DiceFactory {
    override fun create(): Dice = when (Random.nextInt(1, 6)) {
        1 -> OneDice
        2 -> TwoDice
        3 -> ThreeDice
        4 -> FourDice
        5 -> FiveDice
        else -> SixDice
    }

    override fun diceFromValue(value: Int): Dice = when (value) {
        1 -> OneDice
        2 -> TwoDice
        3 -> ThreeDice
        4 -> FourDice
        5 -> FiveDice
        else -> SixDice
    }
}