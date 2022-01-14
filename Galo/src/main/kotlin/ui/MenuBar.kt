package ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar

/**
 * MunuBar of the game.
 * @param status To consult the availability of operations.
 * @param onNew Called when the item Game>New is selected.
 * @param onJoin Called when the item Game>New is selected.
 * @param onRefresh Called when the item Game>New is selected.
 * @param onExit Called when the item Game>Exist is selected.
 */
@Composable
fun FrameWindowScope.GaloMenuBar(
    //status: GameStatus,
    onNew: ()->Unit,
    onJoin: ()->Unit,
    onExit: ()->Unit
) =  MenuBar {
    Menu("Game",'G') {
        Item("New", onClick =  onNew )
        Item("Join", onClick =  onJoin )
        //Item("Refresh", onClick =  onRefresh, enabled = status.game!=null )
        Item("Exit", onClick = onExit )
    }
}