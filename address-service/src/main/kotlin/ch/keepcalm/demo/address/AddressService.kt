package ch.keepcalm.demo.address

import org.springframework.stereotype.Service

@Service
class AddressService(private val repository: AddressRepository) {

    fun findCustomerById(id: String): Address? {
        return repository.findById(id).orElse(null)
    }

    fun findCustomers() : List<Address> {
        return repository.findAddresses()
    }

    fun post(address: Address) {
        repository.save(address)
    }
}
