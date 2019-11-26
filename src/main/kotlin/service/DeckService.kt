package service

import arrow.core.fix
import configuration.server.RepositoryContext
import exceptions.EntityNotFoundException
import io.reactivex.Flowable
import model.Deck

fun save(deck: Deck) = repository.save(deck).run(RepositoryContext).fix().extract()

fun getById(id: String) = repository.getById(id).run(RepositoryContext).fix().extract() ?: throw EntityNotFoundException()

fun getByLesson(lessonId: String) =
    Flowable.fromPublisher(repository.getByLesson(lessonId).run(RepositoryContext).fix().extract().publisher)

fun delete(id: String) = repository.delete(id).run(RepositoryContext)

fun replace(id: String, deck: Deck) = repository.replace(id, deck).run(RepositoryContext).fix().extract()
