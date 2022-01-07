package ui

import androidx.compose.runtime.*
import db.Operations
import kotlinx.coroutines.*
import model.Game
import model.Move
import model.Player
import model.Position

/**
 * Represents the game state in the context of the WindowContent view.
 * @property opers Basic operations for persistence.
 * @property scope CoroutineScope of composition to launch coroutines.
 * @property name Game name indicated in new or join operations.
 * @property player Player of this game side.
 * @property game Internal state of game.
 * @property moves Plays made since the beginning of the game.
 * @constructor Receive [opers] and [scope] properties as parameters.
 */
class GameViewStatus( private val opers: Operations, private val scope: CoroutineScope ) {
    private var name: String="Unknown"
    var player by mutableStateOf<Player?>(null)
        private set
    var game by mutableStateOf<Game?>(null)
        private set
    private var moves: List<Move> = emptyList()
    /**
     * Create a new game where the player is the cross (first player).
     * @param gameName Name of the game to create.
     */
    fun new(gameName: String) {
        name = gameName
        player = Player.CROSS
        game = Game()
        moves = emptyList()
        opers.save(name, emptyList())
    }
    /**
     * Open the game where the player is the circle (second player).
     * @param gameName Name of the game to load.
     */
    fun join(gameName :String) {
        name = gameName
        moves = opers.load(name)
        player = Player.CIRCLE
        val g = Game( moves )
        game = g
        if ( player != g.turn ) waitForOther()
    }
    /**
     * Try to make a move in the indicated [position].
     * @param position Where to make the move
     */
    fun tryPlay(position: Position) {
        val g = game ?: return
        if (g.turn!=player) return
        val g2 = g.tryPlay(position)
        if (g2 === g) return
        moves = moves + Move(position,g.turn)
        game = g2
        opers.save(name, moves)
        waitForOther()
    }
    /**
     * Perform refresh operations, every 3 seconds, until there are more moves (the other player plays).
     * This function launch a coroutine and suspends its execution.
     * The suspended code will be executed in an IO thread.
     */
    private fun waitForOther() {
        val g = game ?: return
        if (g.isOver) return
        scope.launch(Dispatchers.IO) {
            do {
                delay(3000)
                moves = opers.load(name)
            } while (moves.size==g.numberOfPlays)
            //game = Game(moves)
        }
        game = Game(moves)
    }
}