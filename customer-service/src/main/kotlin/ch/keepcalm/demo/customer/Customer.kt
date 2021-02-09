package ch.keepcalm.demo.customer

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "CUSTOMER")
data class Customer(@Id val id: String? = null, val firstName: String, val lastName: String)
