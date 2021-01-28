package ch.keepcalm.demo.address

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URI

@RestController
class AddressController(private val addressService: AddressService) {

    @RequestMapping("/addresses")
    fun addresses(): ResponseEntity<EntityModel<String>> {
        return addressService.addresses()
    }
    @RequestMapping("/foo")
    fun foo(): ResponseEntity<String> {
        return addressService.foo()
    }
}

@Service
class AddressService(private val restTemplate: RestTemplate) {

    fun addresses(): ResponseEntity<EntityModel<String>> {
        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
        return restTemplate.exchange(
            addressesLink.expand().href,
            HttpMethod.GET,
            null,
            TypeReferences.EntityModelType<String>()
        )
    }

    internal fun traverseToInternalApiAuftragService(): Traverson {
        return Traverson(
            URI.create("http://address-service:8081"),
            MediaTypes.HAL_JSON
        ).setRestOperations(restTemplate)
    }

    fun foo(): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers["Accept"] = MediaType.APPLICATION_JSON_VALUE

        val baseUrl = "http://address-service:8081/"
        return restTemplate.exchange(baseUrl, HttpMethod.GET,  HttpEntity<Any>(headers), String::class.java)
    }
}





//
//class ConsumerControllerClient {
//    @get:Throws(RestClientException::class, IOException::class)
//    val employee: Unit
//        get() {
//            val baseUrl = "http://producer:8080/employee"
//            val restTemplate = RestTemplate()
//            var response: ResponseEntity<String?>? = null
//            try {
//                response = restTemplate.exchange(baseUrl, HttpMethod.GET, headers, String::class.java)
//            } catch (ex: Exception) {
//                println(ex)
//            }
//            println(response!!.body)
//        }
//
//    companion object {
//        @get:Throws(IOException::class)
//        private val headers: HttpEntity<*>
//            private get() {
//                val headers = HttpHeaders()
//                headers["Accept"] = MediaType.APPLICATION_JSON_VALUE
//                return HttpEntity<Any>(headers)
//            }
//    }
//}




//@Service
//class FooService(private val discoveryClient: EurekaClient, private val restTemplate: RestTemplate) {
//
//    fun apiEndpoint() = discoveryClient.getNextServerFromEureka("address", false).homePageUrl
//
//    fun addresses(): ResponseEntity<EntityModel<String>> {
//        val url = apiEndpoint()
//        println(url)
//
//        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
//        return restTemplate.exchange(
//            addressesLink.expand().href,
//            HttpMethod.GET,
//            null,
//            TypeReferences.EntityModelType<String>()
//        )
//    }
//
//    internal fun traverseToInternalApiAuftragService(): Traverson {
//        return Traverson(
//            URI.create("http://localhost:8081"),
//            MediaTypes.HAL_JSON
//        ).setRestOperations(restTemplate)
//    }
//}
