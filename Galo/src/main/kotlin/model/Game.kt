package model

const val GAME_DIM = 3

/**
 * Represents a move performed by a player in a position.
 */
data class Move(val pos:Position, val player: Player)

/**
 * Represents the (immutable) board of the game.
 * The game will only have one instance of this type, which will be exchanged for another in each move performed.
 */
private class Board{
    private val plays: Array< Array<Player?> >
    private val counter: Int
    val isCompleted: Boolean get() = counter==GAME_DIM*GAME_DIM

    /**
     * Build the starting board
     */
    constructor() {
        plays = Array(GAME_DIM) { arrayOfNulls(GAME_DIM) }
        counter = 0
    }
    /**
     *  Build the next board after a move.
     *  Assumes the previous board is discarded.
     */
    constructor(old: Board, move: Move) {
        plays = old.plays // Optimization: Because the old board is not used and will be discarded.
        //plays = Array(GAME_DIM) { l-> Array(GAME_DIM) { c-> old.plays[l][c] } }
        counter = old.counter +1
        plays[move.pos.line][move.pos.column] = move.player
    }
    operator fun get(l: Int, c: Int) = plays[l][c]
    operator fun get(pos: Position) = plays[pos.line][pos.column]
}

/**
 * Represents the complete game.
 * @property board The board with the moves already taken
 * @property turn Next player to play
 * @property isOver Indicates if the game is over (Only if the board is full)
 */
class Game {
    private val board: Board
    val turn: Player
    val isOver get() = board.isCompleted // TODO: if someone won

    /**
     * Builds the initial game.
     */
    constructor() {
        board = Board()
        turn = Player.CROSS
    }
    private constructor(board: Board, turn: Player) {
        this.board = board
        this.turn = turn
    }

    /**
     * Check the possibility to make a move in this position.
     */
    fun canPlay(pos: Position) = board[pos]==null

    /**
     * Make a move in the indicated position.
     * @return The next Game after that move.
     */
    fun play(pos: Position) = Game( Board(board,Move(pos,turn)), turn.other )

    /**
     * Attempts to make the move in the indicated position.
     * @return The game after the move or the actual game if the move it is not possible.
     */
    fun tryPlay(pos: Position) = if (canPlay(pos)) play(pos) else this

    /**
     * TGets the play from the indicated position.
     * @return The player who made the move in that position or null if that position on the board is empty.
     */
    operator fun get(pos: Position) = board[pos]
}
