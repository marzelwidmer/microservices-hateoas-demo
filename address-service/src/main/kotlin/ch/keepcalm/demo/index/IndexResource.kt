package ch.keepcalm.demo.index

import ch.keepcalm.demo.address.AddressResource
import ch.keepcalm.demo.customer.CustomerResource
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.BasicLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexResource() {

    val CUSTOMERS_REL = "customers"
    val CUSTOMER_REL = "customer"
    val ADDRESSES_REL = "addresses"
    val API_DOCS_REL = "api-docs"

    @GetMapping
    fun index(): EntityModel<Unit> {
        val selfLink: Link = linkTo(methodOn(IndexResource::class.java).index()).withSelfRel()
        return EntityModel.of(Unit, selfLink).apply {
            add(Link.of("${BasicLinkBuilder.linkToCurrentMapping()}/docs/api-guide.html").withRel(API_DOCS_REL))
            add(linkTo(methodOn(CustomerResource::class.java).all()).withRel(CUSTOMERS_REL))
            add(Link.of("${BasicLinkBuilder.linkToCurrentMapping()}/customers/{id}").withRel(CUSTOMER_REL))
            add(linkTo(methodOn(AddressResource::class.java).addresses()).withRel(ADDRESSES_REL))
        }
    }
}
