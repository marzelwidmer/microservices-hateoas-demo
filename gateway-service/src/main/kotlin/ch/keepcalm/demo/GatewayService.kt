package ch.keepcalm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.Buildable
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.context.annotation.Bean


@SpringBootApplication
class GatewayService {

	@Bean
	fun myRoutes(builder: RouteLocatorBuilder): RouteLocator? {
		return builder.routes() // Add a simple re-route from: /get to: http://httpbin.org:80
			// Add a simple "Hello:World" HTTP Header
			.route { p: PredicateSpec ->
				p
					.path("/get") // intercept calls to the /get path
					.filters { f: GatewayFilterSpec ->
						f.addRequestHeader(
							"Hello",
							"World"
						)
					} // add header
					.uri("http://httpbin.org:80")
			} // forward to httpbin
			.build()
	}

}

fun main(args: Array<String>) {
	runApplication<GatewayService>(*args)
}
