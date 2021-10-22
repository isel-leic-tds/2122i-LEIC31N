package tennis

//import io.*
import readln
import tennis.oo.*

enum class Player{ A, B }

fun main() {
    var game: Score = InitialScore
    while(true) {
        game.print()
        if (game.isGame()) break
        game = game.next( readWinPlayer() )
    }
}

fun readWinPlayer(): Player {
    print("Point for player A (Y/N)? ")
    return if (readln().first() in "yY") Player.A else Player.B
}