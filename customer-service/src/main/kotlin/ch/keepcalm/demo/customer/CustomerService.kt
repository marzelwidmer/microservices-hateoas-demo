package ch.keepcalm.demo.customer

import ch.keepcalm.demo.customer.secureByDesignModel.Customer
import org.springframework.stereotype.Service

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun findCustomerById(id: String) = repository.findById(id).orElse(null).toDomainObject()

    fun findCustomers() = repository.findCustomers().map { it.toDomainObject() }

    fun post(customer: Customer) = repository.save(CustomerEntity(firstName = customer.firstName.toString(), lastName = customer.lastName.toString()))

}
