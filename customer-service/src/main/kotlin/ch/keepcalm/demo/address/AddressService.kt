package ch.keepcalm.demo.address

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
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

        val headers = HttpHeaders()
        val selfLink: Link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController::class.java).addresses()).withSelfRel()
        println(selfLink.href)
        headers.location = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController::class.java).addresses()).toUri()
        println(ResponseEntity<EntityModel<String>>(headers, HttpStatus.OK))
        return addressService.addresses()
    }
}

@Service
class AddressService(private val restTemplate: RestTemplate) {

    fun addresses(): ResponseEntity<EntityModel<String>> {
        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
        return restTemplate.exchange(
            addressesLink.expand().href, HttpMethod.GET, null, TypeReferences.EntityModelType<String>()
        )
    }

    internal fun traverseToInternalApiAuftragService(): Traverson {
        return Traverson(URI.create("http://address-service"), MediaTypes.HAL_JSON).setRestOperations(restTemplate)
    }
}
