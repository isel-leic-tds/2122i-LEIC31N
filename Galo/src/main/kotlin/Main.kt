import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import db.MongoDriver
import ui.WindowContent

/**
 * Application entry point of the game.
 * Creates the driver for mongoDB, ensuring it is closed at the end of the application.
 * Launches the main application window in Desktop Compose.
 */
fun main() {
    MongoDriver().use { driver ->
        application {
            val winState = WindowState(width = Dp.Unspecified, height = Dp.Unspecified)
            Window(
                onCloseRequest = ::exitApplication,
                state = winState,
                title = "Jogo do Galo"
            ) {
                WindowContent(driver, onExit = ::exitApplication)
            }
        }
    }
}

