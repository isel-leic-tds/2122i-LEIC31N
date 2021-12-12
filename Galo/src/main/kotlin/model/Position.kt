package model

/**
 * Represents a position on the game grid.
 * Internally it stores only the general index in the grid.
 * @property line Grid line for this position
 * @property column Grid column for this position.
 */
class Position private constructor(private val index:Int) {
    val line get() = index / GAME_DIM
    val column get() = index % GAME_DIM

    companion object {
        // All positions.
        val values = (0 until GAME_DIM * GAME_DIM).map { Position(it) }
        // Fake constructor to get a position
        operator fun invoke(l: Int, c:Int) = values[ l * GAME_DIM + c]
    }
    override fun toString() = "($line,$column)"
}