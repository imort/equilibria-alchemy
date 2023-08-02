package tg

import com.google.cloud.datastore.Datastore
import com.google.cloud.datastore.Entity
import com.google.cloud.datastore.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class TelegramService(
    private val dispatcher: CoroutineDispatcher,
    private val datastore: Datastore,
) {
    private val keyFactory = datastore
        .newKeyFactory()
        .setKind(KIND)
    private val query = Query.newEntityQueryBuilder()
        .setKind(KIND)
        .build()

    suspend fun register(id: Long): Entity? = withContext(dispatcher) {
        val key = keyFactory.newKey(id)
        val entity = Entity.newBuilder(key)
            .set("limit", TelegramUser.LIMIT_DEFAULT)
            .build()
        datastore.add(entity)
    }

    suspend fun unregister(id: Long): Boolean = withContext(dispatcher) {
        val key = keyFactory.newKey(id)
        datastore.delete(key)
        true
    }

    suspend fun limit(id: Long, value: Long): Entity? = withContext(dispatcher) {
        val key = keyFactory.newKey(id)
        val entity = Entity.newBuilder(key)
            .set("limit", value)
            .build()
        datastore.put(entity)
    }

    suspend fun users(): List<TelegramUser> = withContext(dispatcher) {
        val users = mutableListOf<TelegramUser>()
        datastore.run(query).forEach {
            users += TelegramUser(
                id = it.key.id,
                limit = it.getLong("limit"),
            )
        }
        users
    }

    companion object {
        const val KIND = "telegram"
    }
}