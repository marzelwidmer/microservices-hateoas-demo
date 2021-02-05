package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	description "Should return the Index Resource of address"
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
