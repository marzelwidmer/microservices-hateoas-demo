package ch.keepcalm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import org.springframework.hateoas.config.EnableHypermediaSupport

@SpringBootApplication
@EnableDiscoveryClient
@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
class GatewayService

fun main(args: Array<String>) {
	runApplication<GatewayService>(*args) {
		addInitializers(
			beans {}
		)
	}
}
