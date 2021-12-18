package model

import db.Operations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Represents the game state with persistence for one of the players.
 * @property opers Basic operations for persistence
 * @property player Player of this game side.
 * @property game Internal state of game.
 * @property moves Plays made since the beginning of the game.
 */
data class GameStatus(
    val opers: Operations,
    val player: Player?=null,
    val game: Game? =null,
    val moves: List<Move> = emptyList()
) {
    /**
     * Create a new game where the player is the cross (first player).
     */
    fun new(): GameStatus {
        opers.save(emptyList())
        return GameStatus(opers, Player.CROSS, Game())
    }

    /**
     * Open the game where the player is the circle (second player).
     */
    fun join(): GameStatus {
        val moves = opers.load()
        return GameStatus(opers, Player.CIRCLE, Game(moves), moves)
    }

    /**
     * Actualize the game, checking if the other player has already played.
     */
    fun refresh(): GameStatus {
        if (game==null) return this
        val moves = opers.load()
        return if (moves.size==game.numberOfPlays) this
        else GameStatus(opers, player, Game(moves), moves)
    }

    /**
     * Try to make a move in the indicated [position].
     */
    fun tryPlay(position: Position): GameStatus {
        if (game==null || game.turn!=player) return this
        val g = game.tryPlay(position)
        if (g === game) return this
        val moves = this.moves + Move(position,game.turn)
        opers.save( moves )
        return GameStatus(opers,player,g, moves)
    }

    /**
     * Perform refresh operations, every 3 seconds, until there are more moves (the other player plays).
     * This function has to be called in a coroutine and it suspends its execution.
     * The suspended code will be executed in an IO thread.
     * @return The new state, with the other player's move.
     */
    suspend fun waitForOther(): GameStatus {
        if (game==null || game.turn == player) return this
        //println("waitForOther Start: ${Thread.currentThread().name}")
        return withContext(Dispatchers.IO) {
            var gs: GameStatus
            do {
                delay(3000)
                gs = refresh()
            } while (gs === this@GameStatus)
            //println("waitForOther Exit: ${Thread.currentThread().name}")
            gs
        }
    }
}