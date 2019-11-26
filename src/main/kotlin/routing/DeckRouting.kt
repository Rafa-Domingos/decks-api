package routing

import exceptions.InsufficientParametersException
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondTextWriter
import io.ktor.routing.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactive.collect
import org.litote.kmongo.json
import service.*

@ExperimentalCoroutinesApi
fun Application.deckRoutes() {
    routing {
        get("/{id}") {
            call.respond(HttpStatusCode.OK, getById(call.parameters["id"]!!))
        }

        get("/{lessonId}") {
            call.respondTextWriter(ContentType.Text.Plain) {
                (call.request.queryParameters["lessonId"] ?: throw InsufficientParametersException()).let { lessonId ->
                    getByLesson(lessonId).collect { deck ->
                        this.write("data: ${deck.json}\n")
                        this.flush()
                    }
                }
            }
        }

        post {
            call.response.let {
                it.headers.append("Location", "/${save(call.receive())._id}", true)
                it.status(HttpStatusCode.Created)
            }
        }

        delete("/{id}") {
            delete(call.parameters["id"]!!)
            call.response.status(HttpStatusCode.NoContent)
        }

        put("/{id}") {
            replace(call.parameters["id"]!!, call.receive())
            call.response.status(HttpStatusCode.NoContent)
        }
    }
}