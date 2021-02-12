package ch.keepcalm.demo.customer

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "CUSTOMER")
data class CustomerEntity(@Id val id: String? = null, val firstName: String, val lastName: String) {
    companion object {
        fun fromDomainObject(customer: CustomerAggregate) = CustomerEntity(id = customer.id, firstName = customer.firstName.toString(), lastName = customer.lastName.toString())
    }

    fun toDomainObject() = CustomerResource(id = this.id, firstName = this.firstName, lastName = this.lastName)
}
