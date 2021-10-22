package tennis.first

import tennis.Player

enum class Points(val number: Int){ LOVE(0), FIFTEEN(15), THIRTY(30), FORTY(40), GAME(45);
    fun advance() = values()[ordinal+1]
}

class Score(val pointsOfA: Points, val pointsOfB: Points) {
    fun print() {
        println( when {
            pointsOfA=== Points.FORTY && pointsOfB=== Points.FORTY -> "Deuce"
            pointsOfA=== Points.GAME && pointsOfB!== Points.FORTY -> "Game for A"
            pointsOfB=== Points.GAME && pointsOfA!== Points.FORTY -> "Game for B"
            pointsOfA=== Points.GAME && pointsOfB=== Points.FORTY -> "Advantage A"
            pointsOfB=== Points.GAME && pointsOfA=== Points.FORTY -> "Advantage B"
            else -> "${pointsOfA.number} - ${pointsOfB.number}"
        } )
    }
    fun isGame() =
        pointsOfA=== Points.GAME && pointsOfB!== Points.FORTY ||
        pointsOfB=== Points.GAME && pointsOfA!== Points.FORTY

    fun next(win: Player): Score = when {
        pointsOfA=== Points.FORTY && pointsOfB=== Points.FORTY -> // Deuce
            if (win === Player.A) Score(Points.GAME, pointsOfB)
            else Score(pointsOfA, Points.GAME)
        isGame() -> throw IllegalStateException()
        pointsOfA=== Points.GAME && pointsOfB=== Points.FORTY -> // Advantage A
            if (win === Player.A) Score(pointsOfA, Points.LOVE)
            else Score(Points.FORTY, Points.FORTY)
        pointsOfB=== Points.GAME && pointsOfA=== Points.FORTY -> // Advantage B
            if (win === Player.B) Score(Points.LOVE, pointsOfB)
            else Score(Points.FORTY, Points.FORTY)
        else ->
            if (win === Player.A) Score(pointsOfA.advance(), pointsOfB)
            else Score(pointsOfA, pointsOfB.advance())
    }
}

val InitialScore = Score(Points.LOVE, Points.LOVE)