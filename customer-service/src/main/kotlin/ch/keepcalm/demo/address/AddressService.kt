package ch.keepcalm.demo.address

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import com.netflix.appinfo.InstanceInfo
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class AddressController(private val addressService: AddressService) {

    @RequestMapping("/addresses")
    fun addresses(): ResponseEntity<EntityModel<String>> {
        return addressService.addresses()
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
            URI.create("http://localhost:8081"),
            MediaTypes.HAL_JSON
        ).setRestOperations(restTemplate)
    }
}

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
