package ch.keepcalm.demo.address

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "ADDRESS")
data class Address(@Id val id: String? = null, val streetName: String, val streetNr: String)
