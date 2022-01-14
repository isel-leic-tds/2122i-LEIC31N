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
    val waiting: Boolean get() = waitingJob!=null
    private var waitingJob: Job? = null
    /**
     * Create a new game where the player is the cross (first player).
     * @param gameName Name of the game to create.
     */
    fun new(gameName: String) {
        stopWait()
        name = gameName
        player = Player.CROSS
        game = Game()
        moves = emptyList()
        scope.launch { opers.save(name, emptyList()) }
    }
    /**
     * Open the game where the player is the circle (second player).
     * @param gameName Name of the game to load.
     */
    fun join(gameName :String) {
        stopWait()
        name = gameName
        player = Player.CIRCLE
        scope.launch {
            moves = opers.load(name)
            val g = Game(moves)
            game = g
            if (player != g.turn) waitForOther()
        }
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
        scope.launch {
            opers.save(name, moves)
            waitForOther()
        }
    }
    /**
     * Loads game moves, every 3 seconds, until there are more moves (when opponent plays).
     * This function starts a coroutine and suspends its execution.
     * The Job object allows canceling the wait, if necessary.
     */
    private fun waitForOther() {
        val g = game
        if (g==null || g.isOver) return
        waitingJob = scope.launch {
            do {
                delay(3000)
                moves = opers.load(name)
            } while (moves.size==g.numberOfPlays)
            game = Game(moves)
            waitingJob = null
        }
    }

    /**
     * Cancels the waiting for opponent's play.
     */
    private fun stopWait() {
        val job = waitingJob ?: return
        job.cancel()
        waitingJob = null
    }
}