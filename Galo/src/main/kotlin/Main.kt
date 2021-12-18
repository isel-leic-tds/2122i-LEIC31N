import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import db.FileOpers
import kotlinx.coroutines.launch
import model.Game
import model.GameStatus
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
        val scope = rememberCoroutineScope()
        var status by remember { mutableStateOf(GameStatus(FileOpers("game.txt"))) } // The state of the game
        DesktopMaterialTheme {
            GaloMenuBar(
                status,
                onNew = { status = status.new() },
                onJoin = { status = status.join() },
                onRefresh = { status = status.refresh() },
                onExit = ::exitApplication
            )
            Column {
                GaloView(status.game) { pos ->
                    status = status.tryPlay(pos)
                    //println("tryPlay: ${Thread.currentThread().name}")
                    scope.launch { status = status.waitForOther() }
                }
                StatusView(status)
            }
        }
    }
}
