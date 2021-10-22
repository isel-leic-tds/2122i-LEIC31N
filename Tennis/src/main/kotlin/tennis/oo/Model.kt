package tennis.oo

import tennis.Player

enum class Points(val number: Int){ LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40);
    fun advance() = values()[ordinal+1]
}

sealed class Score {
    abstract fun print()
    open fun isGame() = false
    abstract fun next(win: Player): Score
}
class ByPoints(val pointsOfA: Points, val pointsOfB: Points): Score() {
    override fun print() { println("${pointsOfA.number} - ${pointsOfB.number}") }
    override fun next(win: Player): Score = when {
        pointsOfA===Points.FORTY && pointsOfB===Points.THIRTY && win==Player.B -> Deuce
        pointsOfB===Points.FORTY && pointsOfA===Points.THIRTY && win==Player.A -> Deuce
        pointsOfA===Points.FORTY && win==Player.A -> Game(Player.A)
        pointsOfB===Points.FORTY && win==Player.B -> Game(Player.B)
        else -> if (win === Player.A) ByPoints(pointsOfA.advance(), pointsOfB)
                else ByPoints(pointsOfA, pointsOfB.advance())
    }
}
object Deuce : Score() {
    override fun print() { println("Deuce") }
    override fun next(win: Player) = Advantage(win)
}
class Game(val winner: Player) : Score() {
    override fun print() { println("Game of ${winner.name}") }
    override fun isGame() = true
    override fun next(win: Player):Score { throw IllegalStateException() }
}
class Advantage(val of: Player) : Score() {
    override fun print() { println("Advantage of ${of.name}") }
    override fun next(win: Player) = if (win===of) Game(win) else Deuce
}

val InitialScore = ByPoints(Points.LOVE, Points.LOVE)