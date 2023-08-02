import com.google.cloud.datastore.DatastoreOptions
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.Dispatchers
import model.AlchemyInfo
import model.MoralisEvent
import tg.TelegramHandler
import tg.TelegramService

fun main(args: Array<String>) {
    println("Starting..")
    println("Program arguments: ${args.joinToString()}")
    EngineMain.main(args)
}

fun Application.module() {
    val datastore = DatastoreOptions.getDefaultInstance().service
    val handlers = listOf(
        TelegramHandler(TelegramService(Dispatchers.IO, datastore)),
    )

    suspend fun PipelineContext<*, ApplicationCall>.handleSafely(block: suspend () -> List<Event>) {
        try {
            val events = block()
            if (events.isNotEmpty()) {
                println("Processing events: ${events.size}")
                handlers.onEach { handler ->
                    handler.dispatch(events)
                }
            }
            call.respond(HttpStatusCode.OK)
        } catch (t: Throwable) {
            call.respond(HttpStatusCode.InternalServerError)
            t.printStackTrace()
        }
    }

    install(ContentNegotiation) {
        json(Dependencies.json)
    }
    routing {
        get("_ah/warmup") {
            call.respond(HttpStatusCode.OK)
        }
        post(Regex("""alchemy/(?<network>.+)""")) {
            handleSafely {
                val network = call.parameters["network"] ?: error("Missing network in ${call.parameters}")
                call.receive<AlchemyInfo>()
                    .event.data.block.logs
                    .map { Event.from(network, it) }
            }
        }
        post("moralis") {
            handleSafely {
                val response = call.receive<MoralisEvent>()
                if (!response.confirmed) return@handleSafely emptyList() // allow only confirmed events
                val network = response.chainId.label
                response.logs.map { Event.from(network, it) }
            }
        }
    }
}