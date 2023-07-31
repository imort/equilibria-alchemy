package db

import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun create(): Database {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/database"
        return Database.connect(jdbcURL, driverClassName)
    }
}