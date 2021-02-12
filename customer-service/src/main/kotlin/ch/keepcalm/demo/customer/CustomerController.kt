package ch.keepcalm.demo.customer

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

// Exposed to the Client
data class CustomerResource(val id: String? = UUID.randomUUID().toString(), val firstName: String, val lastName: String)

@RestController
class CustomerController(private val service: CustomerService) {
    companion object REL {
        const val CREATE_REL = "create"
        const val ALL_CUSTOMERS_REL = "customers"
    }

    @GetMapping(value = ["/customers/{id}"], produces = [MediaTypes.HAL_JSON_VALUE])
    fun oneCustomer(@PathVariable id: String): EntityModel<CustomerResource> {
        val selfLink = linkTo(methodOn(CustomerController::class.java).oneCustomer(id)).withSelfRel()

        val customer = CustomerResource(id=Any().toString(), firstName = Any().toString(), lastName = Any().toString())

        val createCustomerLink = linkTo(methodOn(CustomerController::class.java).addCustomer(customer)).withRel(CREATE_REL)
        val allCustomersLink = linkTo(methodOn(CustomerController::class.java).allCustomers()).withRel(ALL_CUSTOMERS_REL)

        return EntityModel.of(service.findCustomerById(id)).add(selfLink, createCustomerLink, allCustomersLink)
    }

    @GetMapping(value = ["/customers"], produces = [MediaTypes.HAL_JSON_VALUE])
    fun allCustomers(): CollectionModel<EntityModel<CustomerResource>> {
        val selfLink: Link = linkTo(methodOn(CustomerController::class.java).allCustomers()).withSelfRel()
        return CollectionModel.of(
            service.findCustomers().map {
                EntityModel.of(it, linkTo(methodOn(CustomerController::class.java).oneCustomer(it.id!!)).withSelfRel()) // TODO: 12.02.21 !! BAD !! ID !!
            }, selfLink
        )
    }

    @PostMapping(value = ["/customers"])
    fun addCustomer(@RequestBody customerResource: CustomerResource): HttpEntity<*> {


        service.post(customerResource)
        return ResponseEntity.EMPTY
    }
}
