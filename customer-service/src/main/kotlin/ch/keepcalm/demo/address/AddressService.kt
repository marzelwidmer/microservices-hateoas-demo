package ch.keepcalm.demo.address

import org.springframework.beans.factory.annotation.Value
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URI

@RestController
class AddressController(private val addressService: AddressService) {

    @RequestMapping("/addresses")
    fun addresses(): ResponseEntity<EntityModel<String>> {
        val headerLocationURI: URI = linkTo(methodOn(AddressController::class.java).addresses()).toUri()
        val selfLink: Link = linkTo(methodOn(AddressController::class.java).addresses()).withSelfRel()
        val result = addressService.addresses().also {
            it.body?.removeLinks()
            it.body?.add(selfLink)
        }
        return ResponseEntity.ok()
            .headers {
                it.location = linkTo(methodOn(AddressController::class.java).addresses()).toUri()
            }
            .body(result.body);

    }
}

@Service
class AddressService(private val restTemplate: RestTemplate) {

    @Value("\${api.address:http://localhost:9001}")
    private val apiAddress: String? = null

    fun addresses(): ResponseEntity<EntityModel<String>> {
        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
        return restTemplate.exchange(
            addressesLink.expand().href, HttpMethod.GET, null, TypeReferences.EntityModelType<String>()
        )
    }

    internal fun traverseToInternalApiAuftragService(): Traverson {
        return Traverson(URI.create(apiAddress), MediaTypes.HAL_JSON).setRestOperations(restTemplate)
    }
}
