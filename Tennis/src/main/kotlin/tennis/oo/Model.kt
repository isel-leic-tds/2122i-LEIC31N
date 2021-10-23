package tennis.oo

import tennis.Player

private enum class Points(val number: Int){ LOVE(0), FIFTEEN(15), THIRTY(30);
    fun advance() = values()[ordinal+1]
}

sealed class Score {
    fun print() { println(toString()) }
    open fun isGame() = false
    abstract fun next(win: Player): Score
}
private class ByPoints(private val pointsOfA: Points, private val pointsOfB: Points): Score() {
    override fun toString() = "${pointsOfA.number} - ${pointsOfB.number}"
    private fun pointsOf(p:Player) = if(p==Player.A) pointsOfA else pointsOfB
    override fun next(win: Player): Score = when {
        pointsOf(win)===Points.THIRTY -> Forty(win,pointsOf(win.other()))
        else -> if (win === Player.A) ByPoints(pointsOfA.advance(), pointsOfB)
                else ByPoints(pointsOfA, pointsOfB.advance())
    }
}
private class Forty(private val player: Player, private val pointOfOther: Points) : Score() {
    override fun toString() = if( player===Player.A) "40 - ${pointOfOther.number}" else "${pointOfOther.number} - 40"
    override fun next(win: Player) = when {
        win===player -> Game(win)
        pointOfOther===Points.THIRTY -> Deuce
        else -> Forty(player, pointOfOther.advance())
    }
}
private object Deuce : Score() {
    override fun toString() = "Deuce"
    override fun next(win: Player) = Advantage(win)
}
private class Game(private val winner: Player) : Score() {
    override fun toString() = "Game to ${winner.name}"
    override fun isGame() = true
    override fun next(win: Player):Score { throw IllegalStateException() }
}
private class Advantage(private val of: Player) : Score() {
    override fun toString() = "Advantage of ${of.name}"
    override fun next(win: Player) = if (win===of) Game(win) else Deuce
}

val InitialScore: Score = ByPoints(Points.LOVE, Points.LOVE)