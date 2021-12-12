import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import model.Game
import ui.GaloMenuBar
import ui.GaloView
import ui.StatusView

/**
 * Application entry point of the game.
 */
fun main() = application {
    val winState = WindowState(width = Dp.Unspecified, height = Dp.Unspecified)
    Window(
        onCloseRequest = ::exitApplication,
        state = winState,
        title = "Jogo do Galo"
    ) {
        var game by remember { mutableStateOf(Game()) } // The state of the game
        DesktopMaterialTheme {
            GaloMenuBar(
                onNew = { game = Game() },
                onExit = ::exitApplication
            )
            Column {
                GaloView(game) { pos -> game = game.tryPlay(pos) }
                StatusView(game)
            }
        }
    }
}
