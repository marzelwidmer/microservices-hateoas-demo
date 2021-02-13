package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Should return the Index Resources"
	request {
		urlPath("/myapp/services")
		method GET()
	}
	response {
		status 200
		headers {
			contentType "application/hal+json"
		}
		body(
				[
						"_links": [
								"self"     : [
										"href": value(consumer("http://${fromRequest().header('Host')}/myapp/services"), producer("http://localhost:8080/myapp/services"))
								],
								"customer": [
										"href" : value(consumer("http://${fromRequest().header('Host')}/myapp/services/customers"), producer("http://localhost:8080/myapp/services/customer")),
								],
								"address" : [
										"href": value(consumer("http://${fromRequest().header('Host')}/myapp/services/customer/{id}"), producer("http://localhost:8080/myapp/services/address")),
								]
						]
				]
		)
	}
}
