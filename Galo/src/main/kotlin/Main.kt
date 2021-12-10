// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import model.GAME_DIM
import model.Game
import model.Player
import model.Position

/*
@Composable
fun GaloView() {
    Column(Modifier.background(Color.Black).height(620.dp), verticalArrangement = Arrangement.SpaceBetween) {
        repeat(3) {
            Row(Modifier.width(620.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                repeat(3) {
                    Image(painterResource("cross.png"),"cross", modifier = Modifier.size(200.dp))
                }
            }
        }
    }
}
*/
val PLAY_SIDE = 150.dp
val GRID_WIDTH = 5.dp

@Composable
fun PlayView(pos: Position, player: Player?, onClick: () -> Unit) {
    Box(Modifier
        .size(PLAY_SIDE)
        .offset((PLAY_SIDE+GRID_WIDTH)*pos.column, (PLAY_SIDE+GRID_WIDTH)*pos.line)
        .background(Color.White)
        .clickable { onClick() }
    ) {
        if (player == null) return
        val img = if (player == Player.CROSS) "cross" else "circle"
        Image(painterResource("$img.png"), img)
    }
}

@Composable
fun GaloView(game: Game, onClick: (Position)->Unit ) {
    Box(Modifier.background(Color.Black).size(PLAY_SIDE* GAME_DIM+GRID_WIDTH*(GAME_DIM-1))) {
        Position.values.forEach { pos ->
            PlayView(pos, game[pos]) { onClick(pos) }
        }
    }
}

fun main() = application {
    val winState = WindowState(width = Dp.Unspecified, height = Dp.Unspecified)
    Window(
        onCloseRequest = ::exitApplication,
        state = winState,
        title = "Jogo do Galo"
    ) {
        var game by remember { mutableStateOf(Game()) }
        DesktopMaterialTheme {
            GaloView(game) { pos ->
                if (game.canPlay(pos)) game = game.play(pos)
            }
        }
    }
}
