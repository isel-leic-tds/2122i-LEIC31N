package db

import model.Move
import model.toMove

const val COLLECTION_NAME = "Games"

/**
 * Implementation of basic operations for game data persistence using mongoDB documents.
 */
class MongoOpers(driver: MongoDriver) : Operations {
    private data class Game(val _id:String, val moves: List<String>)
    private val collection = driver.getCollection<Game>(COLLECTION_NAME)

    override fun load(gameName: String): List<Move> {
        val game = collection.getDocument(gameName)
        return game?.moves?.map { it.toMove() } ?: emptyList()
    }

    override fun save(gameName: String, moves: List<Move>) {
        val game = Game(gameName, moves.map { it.toText() })
        if (collection.getDocument(gameName)==null)
            collection.insertDocument(game)
        else
            collection.replaceDocument(game)
    }
}