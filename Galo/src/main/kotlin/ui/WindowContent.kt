package ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.window.FrameWindowScope
import db.MongoDriver
import db.MongoOpers
import kotlinx.coroutines.launch
import model.GameStatus

/**
 * Contents of the main game window.
 * Contains the menu, the game grid, the status bar and the dialog (if necessary).
 * @param driver Driver to use mongoDB persistence.
 * @param onExit Function called when the exit option from the menu is chosen.
 */
@Composable
fun FrameWindowScope.WindowContent(driver: MongoDriver, onExit: ()->Unit ) {
    val scope = rememberCoroutineScope()
    //var status by remember { mutableStateOf(GameStatus(FileOpers())) } // The state of the game in local file
    val status = remember { GameViewStatus(MongoOpers(driver),scope) } // The state of the game in mongoDB documents
    var startGame by remember { mutableStateOf<((gameName: String) -> Unit)?>(null) }

    DesktopMaterialTheme {
        GaloMenuBar(
            onNew = { startGame = { status.new(it) } },
            onJoin = { startGame = { status.join(it) } },
            onExit = onExit
        )
        Column {
            GaloView(status.game) { pos -> status.tryPlay(pos) }
            StatusView(status)
        }
        val currStartGame = startGame
        if (currStartGame != null)
            DialogGameName(
                onOk = { currStartGame(it); startGame = null },
                onCancel = { startGame = null }
            )
    }
}