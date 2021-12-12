package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import model.GAME_DIM
import model.Game
import model.Player
import model.Position

val PLAY_SIDE = 150.dp
val GRID_WIDTH = 5.dp
val BOARD_SIDE = PLAY_SIDE * GAME_DIM + GRID_WIDTH *(GAME_DIM -1)

/**
 * Composable element to display a play in a grid position.
 * @param pos Position in grid
 * @param player The present play in this position or null if not have.
 * @param onClick Function called if this position (still empty) is clicked
 */
@Composable
fun PlayView(pos: Position, player: Player?, onClick: () -> Unit) {
    val m = Modifier
        .size(PLAY_SIDE)
        .offset((PLAY_SIDE + GRID_WIDTH)*pos.column, (PLAY_SIDE + GRID_WIDTH)*pos.line)
        .background(Color.White)
    if (player == null)
        Box(m.clickable { onClick() })
    else {
        val img = if (player == Player.CROSS) "cross" else "circle"
        Image(painterResource("$img.png"), img, m)
    }
}

/**
 * Composable to display the game grid.
 * @param game The current state of the game
 * @param onClick Function called when there is an attempt to play in a position.
 */
@Composable
fun GaloView(game: Game, onClick: (Position)->Unit ) {
    Box(Modifier.background(Color.Black).size(BOARD_SIDE)) {
        Position.values.forEach { pos ->
            PlayView(pos, game[pos]) { onClick(pos) }
        }
    }
}