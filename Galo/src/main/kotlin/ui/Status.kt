package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.FrameWindowScope
import model.Game
import model.GameStatus

/**
 * Status Bar of the game.
 */
@Composable
fun FrameWindowScope.StatusView(status: GameStatus) =
    Row(Modifier.width(BOARD_SIDE).background(Color.Yellow), horizontalArrangement = Arrangement.SpaceBetween) {
        val game = status.game
        if (game!=null) {
            Text("Player:${status.player} Turn:${game.turn.letter}")
            if (game.isOver)
                Text("GAME OVER")
        } else
            Text("Select New or Join")
    }