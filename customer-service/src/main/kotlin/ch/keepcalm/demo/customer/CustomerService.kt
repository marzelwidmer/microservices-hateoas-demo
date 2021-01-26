package ch.keepcalm.demo.customer

import org.springframework.stereotype.Service

@Service
class CustomerService(private val repository: CustomerRepository) {

    fun findCustomerById(id: String): Customer? {
        return repository.findById(id).orElse(null)
    }

    fun findCustomers() : List<Customer> {
        return repository.findCustomers()
    }

    fun post(customer: Customer) {
        repository.save(customer)
    }
}
//@Service
//class FooService(private val discoveryClient: EurekaClient, private val restTemplate: RestTemplate) {
//
//    fun apiEndpoint() = discoveryClient.getNextServerFromEureka("address", false).homePageUrl
//
//    fun addresses(): ResponseEntity<EntityModel<String>> {
//        val url = apiEndpoint()
//        println(url)
//
//        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
//        return restTemplate.exchange(
//            addressesLink.expand().href,
//            HttpMethod.GET,
//            null,
//            TypeReferences.EntityModelType<String>()
//        )
//    }
//
//    internal fun traverseToInternalApiAuftragService(): Traverson {
//        return Traverson(
//            URI.create("http://localhost:8081"),
//            MediaTypes.HAL_JSON
//        ).setRestOperations(restTemplate)
//    }
//}
