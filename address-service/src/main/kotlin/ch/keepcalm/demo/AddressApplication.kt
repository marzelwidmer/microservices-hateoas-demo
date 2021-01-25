package ch.keepcalm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AddressApplication

fun main(args: Array<String>) {
	runApplication<AddressApplication>(*args)
}
