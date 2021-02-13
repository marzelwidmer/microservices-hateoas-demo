package ch.keepcalm.demo.base

import ch.keepcalm.demo.GatewayService
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [GatewayService::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = ["server.port=8080"])
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
abstract class BaseClass {
    @LocalServerPort
    var port = 0
    @BeforeEach
    fun setup() {
//        RestAssured.baseURI = "http://localhost:" + port
//        RestAssured.baseURI = "http://localhost:8080"
        RestAssured.basePath = "/"
    }
}
