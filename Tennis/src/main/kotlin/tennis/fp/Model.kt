package tennis.fp

import tennis.Player

private enum class Points(val number: Int){ LOVE(0), FIFTEEN(15), THIRTY(30);
    fun advance() = values()[ordinal+1]
}

sealed class Score
private class ByPoints(val pointsOfA: Points, val pointsOfB: Points): Score()
private class Forty(val player: Player, val pointOfOther: Points) : Score()
private object Deuce : Score()
private class Game(val winner: Player) : Score()
private class Advantage(val of: Player) : Score()

private fun ByPoints.pointsOf(p: Player) = if (p===Player.A) pointsOfA else pointsOfB

fun Score.print() { println( when(this) {
    is Deuce        -> "Deuce"
    is Game         -> "Game to $winner"
    is Advantage    -> "Advantage of $of"
    is Forty        -> if(player===Player.A) "40 - ${pointOfOther.number}" else "${pointOfOther.number} - 40"
    is ByPoints     -> "${pointsOfA.number} - ${pointsOfB.number}"
} ) }

fun Score.next(win: Player) = when(this) {
    is Deuce -> Advantage(win)
    is Advantage -> if (win===of) Game(win) else Deuce
    is Forty -> when {
        win===player -> Game(win)
        pointOfOther===Points.THIRTY -> Deuce
        else -> Forty(player, pointOfOther.advance())
    }
    is ByPoints -> when {
        pointsOf(win)===Points.THIRTY -> Forty(win,pointsOf(win.other()))
        else -> if (win === Player.A) ByPoints(pointsOfA.advance(), pointsOfB)
                else ByPoints(pointsOfA, pointsOfB.advance())
    }
    else -> throw IllegalStateException()
}

fun Score.isGame() = this is Game

val InitialScore: Score = ByPoints(Points.LOVE, Points.LOVE)