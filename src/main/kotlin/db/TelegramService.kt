package db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class TelegramService(private val database: Database) {
    private object Telegram : Table() {
        val id = long("id")
        val limit = long("limit")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Telegram)
        }
    }

    private suspend fun <T> transact(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }

    suspend fun register(id: Long): Boolean = transact {
        Telegram.insert {
            it[this.id] = id
            it[limit] = User.LIMIT_DEFAULT
        }.insertedCount > 0
    }

    suspend fun unregister(id: Long): Boolean = transact {
        Telegram.deleteWhere { Telegram.id eq id } > 0
    }

    suspend fun limit(id: Long, value: Long): Boolean = transact {
        Telegram.update(where = { Telegram.id eq id }) {
            it[limit] = value
        } > 0
    }

    suspend fun users(): List<User> = transact {
        Telegram.selectAll().map(::user)
    }

    private fun user(row: ResultRow) = User(
        id = row[Telegram.id],
        limit = row[Telegram.limit],
    )
}