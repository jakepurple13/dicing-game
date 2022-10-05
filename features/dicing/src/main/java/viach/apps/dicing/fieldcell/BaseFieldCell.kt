package viach.apps.dicing.fieldcell

import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import viach.apps.dicing.dice.Dice

@Parcelize
open class BaseFieldCell(final override val dice: Dice) : FieldCell {
    @IgnoredOnParcel
    override val free: Boolean = dice.value <= 0
}