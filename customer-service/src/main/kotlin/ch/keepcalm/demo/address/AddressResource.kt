package ch.keepcalm.demo.address

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AddressResource(private val addressService: AddressService) {

    @RequestMapping("/addresses")
    fun addresses(): ResponseEntity<EntityModel<String>> {
        val selfLink: Link = linkTo(methodOn(AddressResource::class.java).addresses()).withSelfRel()
        val result = addressService.addresses().also {
            it.body?.removeLinks() // Remove links from down stream service
            it.body?.add(selfLink) // Add new Link from current Service
        }
        return ResponseEntity.ok().headers {
            it.location = linkTo(methodOn(AddressResource::class.java).addresses()).toUri()
        }.body(result.body);
    }
}
