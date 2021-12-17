package model

/**
 * Represents a player or a player's move.
 * @property letter Symbol used by the player.
 */
enum class Player(val letter: Char) {
    CROSS('X'),
    CIRCLE('O');

    val other get() = if (this===CIRCLE) CROSS else CIRCLE
}

/**
 * Converts a Char to Player, assuming the Char is valid.
 */
fun Char.toPlayer() = Player.values().first { it.letter==this }