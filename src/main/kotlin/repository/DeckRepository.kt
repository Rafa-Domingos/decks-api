package repository

import arrow.data.Reader
import arrow.data.ReaderApi.ask
import arrow.data.map
import com.mongodb.client.model.Filters
import configuration.server.RepositoryContext
import kotlinx.coroutines.runBlocking
import model.Deck

fun save(deck: Deck): Reader<RepositoryContext, Deck> =
    ask<RepositoryContext>().map { ctx ->
        runBlocking {
            deck.also {
                ctx.coroutineCollection.insertOne(deck)
            }
        }
    }

fun getById(id: String) =
    ask<RepositoryContext>().map { ctx ->
        runBlocking {
            ctx.coroutineCollection.findOneById(id)
        }
    }

fun getByLesson(lessonId: String) =
    ask<RepositoryContext>().map { ctx ->
        ctx.coroutineCollection.find(Filters.eq("lessonId", lessonId))
    }

fun delete(id: String) =
    ask<RepositoryContext>().map { ctx ->
        runBlocking {
            ctx.coroutineCollection.deleteOneById(id)
        }
    }

fun replace(id: String, deck: Deck) =
    ask<RepositoryContext>().map { ctx ->
        runBlocking {
            ctx.coroutineCollection.replaceOneById(id, deck)
        }
    }
