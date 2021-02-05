package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Should return the Index Resource of customer"
	request {
		urlPath("/")
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
										"href": value(
												consumer("http://${fromRequest().header('Host')}"),
												producer("http://localhost:8080"))
								],
								"api-docs"     : [
										"href": value(
												consumer("http://${fromRequest().header('Host')}/docs/api-guide.html"),
												producer("http://localhost:8080/docs/api-guide.html"))
								],
								 "customer": [
										"href": value(
												consumer("http://${fromRequest().header('Host')}/customers/{id}"),
												producer("http://localhost:8080/customers/{id}")),
										"templated": true
								],
								"customers" : [
										"href": value(
												consumer("http://${fromRequest().header('Host')}/customers"),
												producer("http://localhost:8080/customers")),
										"title": value(
												consumer("A collection of customer"),
												producer("A collection of customer"))
								],
								"addresses": [
										"href": value(
												consumer("http://${fromRequest().header('Host')}/addresses"),
												producer("http://localhost:8080/addresses")),
										"title": value(
												consumer("A collection of addresses"),
												producer("A collection of addresses"))
								]
						]
				]
		)
	}
}
