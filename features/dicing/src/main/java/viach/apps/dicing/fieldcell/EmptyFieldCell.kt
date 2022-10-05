package viach.apps.dicing.fieldcell

import kotlinx.parcelize.Parcelize
import viach.apps.dicing.dice.EmptyDice

@Parcelize
object EmptyFieldCell : BaseFieldCell(EmptyDice)