package ch.keepcalm.demo.customer.secureByDesignModel

import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class CustomerTests {

    @ParameterizedTest(name = "{index} negative test gender {0}")
    @ValueSource(
        chars = ['a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', '1', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', '2', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R',
            's', 'S', 't', 'T', '3', 'V', 'W', 'X', 'Y', 'Z']
    )
    fun `Customer negative creation test - only gender M or F is allowed `(input: Char) {
        assertThrows(IllegalArgumentException::class.java) {
            Customer(id=UUID.randomUUID().toString(), firstName = FirstName(Faker().name.firstName()), lastName = LastName(Faker().name.lastName()), gender = Gender(input))
        }
    }

    @ParameterizedTest(name = "{index} test gender {0}")
    @ValueSource(chars = ['f', 'F', 'm', 'M', 'u', 'U'])
    fun `Customer creation test - it must have a valid gender fF 👧 - mM 🧔 - uU 🤷🏻`(input: Char) {
        Customer(id=UUID.randomUUID().toString(), firstName = FirstName(Faker().name.firstName()), lastName = LastName(Faker().name.lastName()), gender = Gender(input))
    }


    @Test
    fun `test customer firstName that is it not empty should throws IllegalStateException`() {
        assertThrows(IllegalArgumentException::class.java) {
            Customer(id=UUID.randomUUID().toString(), firstName = FirstName(""), lastName = LastName(Faker().name.lastName()), gender = Gender(Faker().gender.unique.shortBinaryTypes().single()))
        }
    }

    @Test
    fun `test customer firstName with 1 characters should throws IllegalStateException`() {
        assertThrows(IllegalArgumentException::class.java) {
            val name = "A"
            Customer(id=UUID.randomUUID().toString(), firstName = FirstName(name), lastName = LastName(Faker().name.lastName()), gender = Gender('F'))
        }
    }

    @Test
    fun `test customer firstName with minimum of 2 characters`() {
        val name = "A".repeat(2)
        Customer(id=UUID.randomUUID().toString(), firstName = FirstName(name), lastName = LastName(Faker().name.lastName()), gender = Gender('F'))
    }

    @Test
    fun `test customer firstName with 20 characters`() {
        val name = "A".repeat(20)
        Customer(id=UUID.randomUUID().toString(), firstName = FirstName(name), lastName = LastName(Faker().name.lastName()), gender = Gender('M'))
    }

    @Test
    fun `test customer firstName with 21 characters should throws IllegalStateException`() {
        assertThrows(IllegalArgumentException::class.java) {
            val name = "A".repeat(21)
            Customer(id=UUID.randomUUID().toString(), firstName = FirstName(name), lastName = LastName(Faker().name.lastName()), gender = Gender('F'))
        }
    }
}
