package model

const val GAME_DIM = 3

enum class Player { CROSS, CIRCLE;
    val other get() = if (this===CIRCLE) CROSS else CIRCLE
}

class Position private constructor(val index:Int) {
    val line get() = index / GAME_DIM
    val column get() = index % GAME_DIM
    companion object {
        val values = (0 until GAME_DIM*GAME_DIM).map { Position(it) }
        operator fun invoke(l: Int, c:Int) = values[ l * GAME_DIM + c]
    }
}

data class Move(val pos:Position, val player: Player)

private class Board{
    private val plays: Array< Array<Player?> >
    constructor() {
        plays = Array(GAME_DIM) { arrayOfNulls(GAME_DIM) }
    }
    constructor(board: Board, move: Move) {
        plays = board.plays
        //plays = Array(GAME_DIM) { l-> Array(GAME_DIM) { c-> board.plays[l][c] } }
        plays[move.pos.line][move.pos.column] = move.player
    }
    operator fun get(l: Int, c: Int) = plays[l][c]
    operator fun get(pos: Position) = plays[pos.line][pos.column]
}

class Game {
    private val board: Board
    val turn: Player

    constructor() {
        board = Board()
        turn = Player.CROSS
    }
    private constructor(board: Board, turn: Player) {
        this.board = board
        this.turn = turn
    }
    fun canPlay(pos: Position) = board[pos]==null
    fun play(pos: Position) = Game( Board(board,Move(pos,turn)), turn.other )
    operator fun get(pos: Position) = board[pos]
}
