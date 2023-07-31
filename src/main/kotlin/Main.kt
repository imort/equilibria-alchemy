import com.google.cloud.datastore.DatastoreOptions
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import tg.TelegramHandler
import tg.TelegramService

fun main(args: Array<String>) {
    println("Starting..")
    println("Program arguments: ${args.joinToString()}")
    EngineMain.main(args)
}

private val scope = CoroutineScope(
    SupervisorJob() + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
)

fun Application.alchemy() {
    val datastore = DatastoreOptions.getDefaultInstance().service
    val handlers = listOf(
        TelegramHandler(TelegramService(Dispatchers.IO, datastore)),
    )

    install(ContentNegotiation) {
        json()
    }

    routing {
        get("_ah/warmup") {
            call.respond(HttpStatusCode.OK)
        }
        post(Regex("""/alchemy/(?<network>.+)""")) {
            try {
                val network = call.parameters["network"] ?: error("Missing network in ${call.parameters}")
                val logs = call.receive<AlchemyInfo>()
                    .event
                    .data
                    .logs
                    .map { EventLog.from(network, it) }
                handlers.onEach { handler ->
                    scope.launch { handler.dispatch(logs) }
                }
                call.respond(HttpStatusCode.OK)
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.InternalServerError)
                t.printStackTrace()
            }
        }
    }
}