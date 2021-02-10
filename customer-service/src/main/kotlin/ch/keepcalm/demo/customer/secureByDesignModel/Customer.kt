package ch.keepcalm.demo.customer.secureByDesignModel

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

data class Customer(val id: String? = UUID.randomUUID().toString(), val firstName: FirstName, val lastName: LastName)

// Domain Primitive
data class FirstName(val value: String) {
    init {
        require(value.isNotEmpty()) { "value must be non-empty" }
        require(value.trim().length >= 2) { "wrong value length" }
        require(value.trim().length <= 20) { "wrong value length" }
    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun create(value: String) = FirstName(value)
    }

    @JsonValue
    override fun toString() = value
}

// Domain Primitive
data class LastName(val value: String) {
    init {
        require(value.isNotEmpty()) { "value must be non-empty" }
        require(value.trim().length >= 2) { "wrong value length" }
        require(value.trim().length <= 20) { "wrong value length" }
    }

    companion object {
        @JvmStatic
        @JsonCreator
        fun create(value: String) = LastName(value)
    }

    @JsonValue
    override fun toString() = value
}
