package ch.keepcalm.demo.customer

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<Customer, String> {

    @Query("select * from customers")
    fun findCustomers(): List<Customer>

}
