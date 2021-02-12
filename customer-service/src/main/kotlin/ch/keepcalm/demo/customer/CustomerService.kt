package ch.keepcalm.demo.customer

import org.springframework.stereotype.Service

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun findCustomerById(id: String) : CustomerResource {
        return repository.findById(id).orElse(null).toDomainObject()
    }

    fun findCustomers() = repository.findCustomers().map {
        CustomerResource(id= it.id, firstName = it.firstName, lastName =it.lastName)
    }

    fun post(customerResource: CustomerResource) = repository.save(
        // TODO: 12.02.21 !! BAD !! ID !!
        CustomerEntity(firstName = customerResource.firstName.toString(), lastName = customerResource.lastName.toString())
    )

}
