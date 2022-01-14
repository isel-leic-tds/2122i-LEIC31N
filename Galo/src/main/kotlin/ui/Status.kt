package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import model.Game
import model.Player

@Composable
fun PlayerView(label:String, player: Player?) = Row {
    Text("$label : ")
    PlayerImage(player, Modifier.padding(2.dp))
}
/**
 * Status Bar of the game.
 */
@Composable
fun FrameWindowScope.StatusView(status: GameViewStatus) =
    Row(Modifier.width(BOARD_SIDE).height(30.dp).background(Color.Yellow), horizontalArrangement = Arrangement.SpaceBetween) {
        val game = status.game
        if (game!=null) {
            //Text("Player:${status.player?.letter} Turn:${game.turn.letter}")
            PlayerView("Player",status.player)
            if (status.waiting) Row {
                Text("Waiting..")
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            }
            PlayerView("Turn",game.turn)
            if (game.isOver)
                Text("GAME OVER")
        } else
            Text("Select New or Join")
    }