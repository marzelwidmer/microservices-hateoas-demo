package ch.keepcalm.demo.base

import ch.keepcalm.demo.CustomerApplication
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@SpringBootTest(classes = [CustomerApplication::class])
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class BaseClass {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

//    @BeforeEach
//    fun setUp(restDocumentation: RestDocumentationContextProvider) {
//        val mockMvc: MockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
//            .build()
//        RestAssuredMockMvc.mockMvc(mockMvc)
//    }

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider, testInfo: TestInfo) {
        RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation).operationPreprocessors()
                .withRequestDefaults(Preprocessors.prettyPrint())
                .withResponseDefaults(Preprocessors.prettyPrint()))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcRestDocumentation.document(javaClass.simpleName + "_" + testInfo.displayName, Preprocessors.preprocessRequest()))
            .build()
        )
    }
}
