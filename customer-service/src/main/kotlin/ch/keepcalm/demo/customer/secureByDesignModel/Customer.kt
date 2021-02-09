package ch.keepcalm.demo.customer.secureByDesignModel

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

data class Customer(val id: String, val firstName: FirstName, val lastName: LastName, val gender: Gender?)

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

// Domain Primitive
data class Gender(@JsonValue val value: Char) {
    init {
        require(value.toLowerCase() == 'm' || value.toLowerCase() == 'f' || value.toLowerCase() == 'u') { "gender ist invalid (m|f|u)" }
    }
}
