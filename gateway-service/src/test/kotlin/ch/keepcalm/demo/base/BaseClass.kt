package ch.keepcalm.demo.base

import ch.keepcalm.demo.GatewayService
import ch.keepcalm.demo.index.IndexResource
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(classes = [GatewayService::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = ["server.port=8080"])
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
abstract class BaseClass {

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider?) {
        RestAssuredWebTestClient.webTestClient(
            WebTestClient.bindToController(
                IndexResource::class
            ).configureClient()
                .filter(
                    documentationConfiguration(restDocumentation)
                        .snippets()
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(Preprocessors.prettyPrint())
                        .withResponseDefaults(Preprocessors.prettyPrint())
                )
                .build()
        )
    }
}
