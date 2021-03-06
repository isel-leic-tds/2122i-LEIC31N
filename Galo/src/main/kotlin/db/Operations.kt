package db

import model.Move

/**
 * Basic operations for game data persistence.
 * Every operation must be idempotent.
 */
interface Operations {
    /**
     * Load all moves.
     * @return all moves read.
     */
    suspend fun load(gameName:String): List<Move>
    /**
     * Save all [moves].
     */
    suspend fun save(gameName:String, moves: List<Move>)
}