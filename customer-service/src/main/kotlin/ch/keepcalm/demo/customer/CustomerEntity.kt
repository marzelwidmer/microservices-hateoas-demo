package ch.keepcalm.demo.customer

import ch.keepcalm.demo.customer.secureByDesignModel.Customer
import ch.keepcalm.demo.customer.secureByDesignModel.FirstName
import ch.keepcalm.demo.customer.secureByDesignModel.LastName
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "CUSTOMER")
data class CustomerEntity(@Id val id: String? = null, val firstName: String, val lastName: String) {
    companion object {
        fun fromDomainObject(customer: Customer) = CustomerEntity(id = customer.id, firstName = customer.firstName.toString(), lastName = customer.lastName.toString())
    }

    fun toDomainObject() = Customer(id = this.id, firstName = FirstName(this.firstName), lastName = LastName(this.lastName))
}
