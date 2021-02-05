package ch.keepcalm.demo.stream

import java.time.LocalDateTime

enum class Type {
    CREATE, DELETE
}

data class Event<K, T>(val type: Type, val eventCreatedAt: LocalDateTime, val key: K, val data: T)



