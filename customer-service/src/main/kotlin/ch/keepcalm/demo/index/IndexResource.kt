package ch.keepcalm.demo.index

import ch.keepcalm.demo.address.AddressResource
import ch.keepcalm.demo.customer.CustomerController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.BasicLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexResource() {

    val ALL_CUSTOMERS_REL = "customers"
    val CUSTOMER_REL = "customer"
    val ALL_ADDRESSES_REL = "addresses"
    val API_DOCS_REL = "api-docs"

    @GetMapping
    fun index(): EntityModel<Unit> {
        val selfLink: Link = linkTo(methodOn(IndexResource::class.java).index()).withSelfRel()
        return EntityModel.of(Unit, selfLink).apply {
            add(Link.of("${BasicLinkBuilder.linkToCurrentMapping()}/docs/api-guide.html").withRel(API_DOCS_REL))
            add(linkTo(methodOn(CustomerController::class.java).allCustomers()).withRel(ALL_CUSTOMERS_REL))
            add(Link.of("${BasicLinkBuilder.linkToCurrentMapping()}/customers/{id}").withRel(CUSTOMER_REL))
            add(linkTo(methodOn(AddressResource::class.java).addresses()).withRel(ALL_ADDRESSES_REL))
        }
    }
}
