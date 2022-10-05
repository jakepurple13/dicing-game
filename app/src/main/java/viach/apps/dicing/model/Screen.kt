package viach.apps.dicing.model

sealed interface Screen {
    val route: String

    abstract class Base(override val route: String) : Screen
    object Menu : Base("Menu")
    object PlayEasyGame : Base("Play Easy Game")
    object PlayNormalGame : Base("Play Normal Game")
    object PlayHardGame : Base("Play Hard Game")
    object TwoPlayersGame : Base("Two Players Game")
    object SavedGame : Base("Saved Game")
    object Stats : Base("Stats")
    object Rules : Base("Rules")
    object Settings : Base("Settings")
}