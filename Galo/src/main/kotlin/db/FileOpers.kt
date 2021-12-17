package db

import model.*
import java.io.File

/**
 * Implementation of basic operations for game data persistence using a local file.
 */
class FileOpers(val fileName: String) : Operations {
    /**
     * Load all moves from file.
     * @return all moves read.
     */
    override fun load() = File(fileName).readLines().map { it.toMove() }

    /**
     * Save all [moves] in file.
     */
    override fun save(moves: List<Move>) =
        File(fileName).writeText( moves.joinToString("\n") { it.toText() } )
}