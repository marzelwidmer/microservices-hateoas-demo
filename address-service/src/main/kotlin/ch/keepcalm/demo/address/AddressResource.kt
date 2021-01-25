package ch.keepcalm.demo.address

import org.springframework.hateoas.*
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.afford
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

@RestController
class AddressResource(private val service: AddressService) {

    @GetMapping(value = ["/addresses/{id}"])
    fun one(@PathVariable id: String): EntityModel<Address> {
        val selfLink: Link = linkTo(
            methodOn(AddressResource::class.java).one(id)
        ).withSelfRel()
        val create: Affordance = afford<AddressResource> {
            methodOn(AddressResource::class.java).add(customer = Address(streetName = "", streetNr = ""))
        }
        val aggregateRoot : Link = linkTo(methodOn(AddressResource::class.java).all()).withRel("addresses")
            .andAffordance(WebMvcLinkBuilder.afford(methodOn(AddressResource::class.java).add(customer = Address(streetName = "", streetNr = ""))))

        return EntityModel.of(service.findCustomerById(id) as Address, selfLink.andAffordance(create), aggregateRoot)
    }


    @GetMapping(value = ["/addresses"])
    fun all(): CollectionModel<Address> {
        val selfLink: Link = linkTo(
            methodOn(AddressResource::class.java).all()
        ).withSelfRel()
        return CollectionModel.of(service.findCustomers(), selfLink)
    }


    @PostMapping(value = ["/addresses"])
    fun add(@RequestBody customer: Address): HttpEntity<*> {
        service.post(customer)
        return  ResponseEntity.EMPTY
    }
}
