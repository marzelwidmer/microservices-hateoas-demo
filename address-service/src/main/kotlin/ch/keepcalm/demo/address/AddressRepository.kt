package ch.keepcalm.demo.address

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository : CrudRepository<Address, String> {

    @Query("select * from Address")
    fun findAddresses(): List<Address>

}
