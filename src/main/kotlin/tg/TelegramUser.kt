package tg

data class TelegramUser(val id: Long, val limit: Long = LIMIT_DEFAULT) {
    companion object {
        const val LIMIT_DEFAULT = 0L
    }
}