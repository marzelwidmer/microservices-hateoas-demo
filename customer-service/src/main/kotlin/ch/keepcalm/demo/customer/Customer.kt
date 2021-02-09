package ch.keepcalm.demo.customer

import ch.keepcalm.demo.customer.secureByDesignModel.Customer
import ch.keepcalm.demo.customer.secureByDesignModel.FirstName
import ch.keepcalm.demo.customer.secureByDesignModel.Gender
import ch.keepcalm.demo.customer.secureByDesignModel.LastName
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "CUSTOMER")
data class CustomerEntity(@Id val id: String? = null, val firstName: String, val lastName: String) {
    companion object {
        fun fromDomainObject(customer: Customer) = CustomerEntity(firstName = customer.firstName.toString() , lastName = customer.lastName.toString())
    }
    fun toDomainObject() = Customer(firstName = FirstName(this.firstName), lastName = LastName(this.lastName), gender = Gender('u'))
}
