package db

import model.*
import java.io.File

/**
 * Implementation of basic operations for game data persistence using local files.
 */
class FileOpers : Operations {
    private fun file(gameName: String) = File("$gameName.txt")
    /**
     * Load all moves from file.
     * @return all moves read.
     */
    override fun load(gameName:String) = file(gameName).readLines().map { it.toMove() }

    /**
     * Save all [moves] in file.
     */
    override fun save(gameName:String, moves: List<Move>) =
        file(gameName).writeText( moves.joinToString("\n") { it.toText() } )
}