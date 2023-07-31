import db.DatabaseFactory
import db.TelegramService
import feature.TelegramHandler
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    println("Starting Equilibria: Alchemy -> Telegram webhook transmitter")
    println("Program arguments: ${args.joinToString()}")
    EngineMain.main(args)
}

private val scope = CoroutineScope(
    SupervisorJob() + CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
)

fun Application.alchemy() {
    val database = DatabaseFactory.create()

    val handlers = listOf(
        TelegramHandler(TelegramService(database)),
    )

    install(ContentNegotiation) {
        json()
    }

    routing {
        post(Regex("""/alchemy/(?<network>.+)""")) {
            try {
                val network = call.parameters["network"] ?: error("Missing network in ${call.parameters}")
                call.receive<AlchemyInfo>()
                    .event
                    .data
                    .logs
                    .map { EventLog.from(network, it) }
                    .onEach { event ->
                        handlers.onEach { handler ->
                            scope.launch { handler.dispatch(event) }
                        }
                    }
                call.respond(HttpStatusCode.OK)
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.InternalServerError)
                t.printStackTrace()
            }
        }
    }
}