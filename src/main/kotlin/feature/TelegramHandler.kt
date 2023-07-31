package feature

import EventLog
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import db.TelegramService

class TelegramHandler(private val service: TelegramService) : Handler {
    private val bot = bot {
        token = "YOUR_API_KEY"
        dispatch {
            command("start") {
                val id = message.chat.id
                if (service.register(id)) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Subscribed")
                }
            }
            command("stop") {
                val id = message.chat.id
                if (service.unregister(id)) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Unsubscribed")
                }
            }
            command("limit") {
                val id = message.chat.id
                val limit = args.single().toLong()
                if (service.limit(id, limit)) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Limit is set to $limit")
                }
            }
        }
    }.apply {
        startPolling()
    }

    override suspend fun dispatch(event: EventLog) {
        for (user in service.users()) {
            if (event.lock.value.toLong() >= user.limit) {
                bot.sendMessage(chatId = ChatId.fromId(user.id), text = event.toString())
            }
        }
    }
}