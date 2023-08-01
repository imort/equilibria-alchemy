package tg

import EventLog
import Handler
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import db.User

class TelegramHandler(private val service: TelegramService) : Handler {
    private val bot = bot {
        token = System.getenv("EQB_TELEGRAM_BOT_TOKEN")
        dispatch {
            command("start") {
                val id = message.chat.id
                if (service.register(id) != null) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Subscribed")
                } else {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Can not register $id for updates")
                }
            }
            command("stop") {
                val id = message.chat.id
                if (service.unregister(id)) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Unsubscribed")
                } else {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Can not unregister $id for updates")
                }
            }
            command("limit") {
                val id = message.chat.id
                val limit = runCatching { args.single().toLong() }.getOrDefault(User.LIMIT_DEFAULT)
                if (service.limit(id, limit) != null) {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Limit is set to $limit")
                } else {
                    bot.sendMessage(chatId = ChatId.fromId(id), text = "Can not set limit $limit for $id")
                }
            }
        }
    }.apply {
        startPolling()
    }

    override suspend fun dispatch(events: List<EventLog>) {
        for (user in service.users()) {
            for (event in events) {
                if (event.lock.value.toLong() >= user.limit) {
                    bot.sendMessage(chatId = ChatId.fromId(user.id), text = event.toString())
                }
            }
        }
    }
}