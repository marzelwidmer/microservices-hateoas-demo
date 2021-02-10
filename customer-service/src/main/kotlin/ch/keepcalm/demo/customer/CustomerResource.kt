package ch.keepcalm.demo.customer

import ch.keepcalm.demo.customer.secureByDesignModel.Customer
import ch.keepcalm.demo.customer.secureByDesignModel.FirstName
import ch.keepcalm.demo.customer.secureByDesignModel.LastName
import org.springframework.hateoas.*
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.afford
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CustomerResource(private val service: CustomerService) {

    @GetMapping(value = ["/customers/{id}"], produces = [MediaTypes.HAL_JSON_VALUE])
    fun one(@PathVariable id: String): EntityModel<Customer> {
        val customer = Customer(id = null, firstName = FirstName("firstName"), lastName = LastName("lastName"))

        val selfLink: Link = linkTo(methodOn(CustomerResource::class.java).one(id)).withSelfRel()
        val create: Affordance = afford<CustomerResource> { methodOn(CustomerResource::class.java).add(customer = customer) }
        val aggregateRoot: Link =
            linkTo(methodOn(CustomerResource::class.java).all()).withRel("customers").andAffordance(WebMvcLinkBuilder.afford(methodOn(CustomerResource::class.java).add(customer = customer)))

        return EntityModel.of(service.findCustomerById(id), selfLink.andAffordance(create), aggregateRoot)
    }

    @GetMapping(value = ["/customers"], produces = [MediaTypes.HAL_JSON_VALUE])
    fun all(): CollectionModel<EntityModel<Customer>> {
        val selfLink: Link = linkTo(methodOn(CustomerResource::class.java).all()).withSelfRel()
        return  CollectionModel.of(
             service.findCustomers().map {
                 EntityModel.of(it, linkTo(methodOn(CustomerResource::class.java).one(it.id.toString())).withSelfRel())
             }, selfLink)
    }

    @PostMapping(value = ["/customers"])
    fun add(@RequestBody customer: Customer): HttpEntity<*> {
        service.post(customer)
        return ResponseEntity.EMPTY
    }
}
