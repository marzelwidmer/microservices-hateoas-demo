package ch.keepcalm.demo.customer

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<CustomerEntity, String> {
    @Query("select * from customer")
    fun findCustomers(): List<CustomerEntity>
}
