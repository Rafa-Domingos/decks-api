package configuration.server

import configuration.database.mongoConnection
import model.Deck
import org.litote.kmongo.coroutine.CoroutineCollection

object RepositoryContext {
    val coroutineCollection: CoroutineCollection<Deck> = mongoConnection()
}