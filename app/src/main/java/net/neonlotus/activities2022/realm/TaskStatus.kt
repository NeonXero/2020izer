package net.neonlotus.activities2022.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import net.neonlotus.activities2022.retrofitexample.QuoteObject

enum class TaskStatus(val displayName: String) {
    Open("Open"),
    InProgress("In Progress"),
    Complete("Complete"),
}

open class Task() : RealmObject() {
    @PrimaryKey
    var name: String = "task"

    @Required
    var status: String = TaskStatus.Open.name

    var statusEnum: TaskStatus
        get() {
            // because status is actually a String and another client could assign an invalid value,
            // default the status to "Open" if the status is unreadable
            return try {
                TaskStatus.valueOf(status)
            } catch (e: IllegalArgumentException) {
                TaskStatus.Open
            }
        }
        set(value) {
            status = value.name
        }
}

open class RealmQuote() : RealmObject() {
    @PrimaryKey
    var id: String = "id"

    var content: String = "content"
    var author: String = "author"
    var length: Int = 0
}