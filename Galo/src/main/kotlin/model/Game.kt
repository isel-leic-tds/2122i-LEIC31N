package model

const val GAME_DIM = 3

/**
 * Represents a move performed by a player in a position.
 */
data class Move(val pos:Position, val player: Player) {
    fun toText() = "${pos.line} ${pos.column} ${player.letter}"
}

fun String.toMove():Move {
    val (l,c,p) = split(' ')
    return Move( Position(l.toInt(),c.toInt()), p.first().toPlayer())
}

/**
 * Represents the (immutable) board of the game.
 * The game will only have one instance of this type, which will be exchanged for another in each move performed.
 */
private class Board{
    private val plays: Array< Array<Player?> >
    val counter: Int
    val isCompleted: Boolean get() = counter==GAME_DIM*GAME_DIM

    /**
     * Build the starting board
     */
    constructor(moves: List<Move> = emptyList()) {
        plays = Array(GAME_DIM) { arrayOfNulls(GAME_DIM) }
        moves.forEach { plays[it.pos.line][it.pos.column] = it.player }
        counter = moves.size
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
    val lastPlay: Position?
    val isOver get() = board.isCompleted || winner()
    val numberOfPlays get() = board.counter

    /**
     * Builds the initial game.
     */
    constructor() {
        board = Board()
        turn = Player.CROSS
        lastPlay = null
    }

    private constructor(board: Board, turn: Player, play:Position) {
        this.board = board
        this.turn = turn
        lastPlay = play
    }

    /**
     * Build the game from a list of moves.
     */
    constructor(moves: List<Move>) {
        board = Board(moves)
        turn = if (moves.size % 2 == 0) Player.CROSS else Player.CIRCLE
        lastPlay = moves.lastOrNull()?.pos
    }

    /**
     * Check the possibility to make a move in this position.
     */
    fun canPlay(pos: Position) = board[pos]==null && !isOver

    /**
     * Make a move in the indicated position.
     * @return The next Game after that move.
     */
    fun play(pos: Position) = Game( Board(board,Move(pos,turn)), turn.other, pos )

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

    /**
     * Auxiliary function to check a sequence of moves from the last player.
     */
    private fun sequence( cond: (Position)->Boolean ) =
        Position.values.filter { cond(it) }.map { board[it] }.count { it==turn.other } == GAME_DIM

    /**
     * Checks if the last move completed a row, a column or one of the diagonals.
     */
    private fun winner():Boolean {
        val lp = lastPlay ?: return false
        return board.counter >= GAME_DIM*2-1 && (
                sequence { it.line==lp.line } ||
                sequence { it.column == lp.column } ||
                sequence { it.column == it.line } ||
                sequence { it.line+it.column== GAME_DIM-1 }
        )
    }
}
