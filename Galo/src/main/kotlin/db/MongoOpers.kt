package db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Move
import model.toMove

const val COLLECTION_NAME = "Games"

/**
 * Implementation of basic operations for game data persistence using mongoDB documents.
 */
class MongoOpers(driver: MongoDriver) : Operations {
    private data class Game(val _id:String, val moves: List<String>)
    private val collection = driver.getCollection<Game>(COLLECTION_NAME)

    override suspend fun load(gameName: String): List<Move> = withContext(Dispatchers.IO) {
        //Thread.sleep(3000)
        val game = collection.getDocument(gameName)
        game?.moves?.map { it.toMove() } ?: emptyList()
    }

    override suspend fun save(gameName: String, moves: List<Move>): Unit = withContext(Dispatchers.IO) {
        //Thread.sleep(3000)
        val game = Game(gameName, moves.map { it.toText() })
        if (collection.getDocument(gameName) == null)
            collection.insertDocument(game)
        else
            collection.replaceDocument(game)
    }
}