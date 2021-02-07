package ch.keepcalm.demo.address

import org.springframework.beans.factory.annotation.Value
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class AddressService(private val restTemplate: RestTemplate) {

    @Value("\${app.address-service:http://localhost:9001}")
    private val appAddressService: String? = null

    fun addresses(): ResponseEntity<EntityModel<String>> {
        val addressesLink = traverseToInternalApiAuftragService().follow("addresses").asLink()
        return restTemplate.exchange(
            addressesLink.expand().href, HttpMethod.GET, null, TypeReferences.EntityModelType<String>()
        )
    }

    internal fun traverseToInternalApiAuftragService(): Traverson {
        return Traverson(URI.create(appAddressService), MediaTypes.HAL_JSON).setRestOperations(restTemplate)
    }
}
