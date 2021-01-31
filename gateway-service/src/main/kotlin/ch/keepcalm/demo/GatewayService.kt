package ch.keepcalm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans

@SpringBootApplication
@EnableDiscoveryClient
class GatewayService

fun main(args: Array<String>) {
	runApplication<GatewayService>(*args) {
		addInitializers(
			beans {}
		)
	}
}
