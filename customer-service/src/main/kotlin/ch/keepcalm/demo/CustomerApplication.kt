package ch.keepcalm.demo

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer
import org.springframework.hateoas.server.core.TypeReferences
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URI


@SpringBootApplication
class CustomerApplication

fun main(args: Array<String>) {
    runApplication<CustomerApplication>(*args)
}

@Configuration
class MyConfiguration {

    @LoadBalanced
    @Bean
    fun restTemplate() = RestTemplate()

//    @Bean
//    fun webClientBuilder(configurer: HypermediaWebClientConfigurer): WebClient.Builder? {
//        return configurer.registerHypermediaTypes(WebClient.builder())
//    }
//
    @Bean
    fun restTemplateCustomizer(configurer: HypermediaRestTemplateConfigurer): RestTemplateCustomizer? {
        return RestTemplateCustomizer { restTemplate: RestTemplate? -> restTemplate?.let { configurer.registerHypermediaTypes(it) } }
    }
}
//
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
//
//@RestController
//class FooController(private val addressService: FooService
////    private val discoveryClient: EurekaClient
//) {
//
//    @RequestMapping("/addresses")
//    fun addresses(): ResponseEntity<EntityModel<String>> {
//        return addressService.addresses()
//    }


//    fun serviceUrl(): String? {
//        val instance = discoveryClient!!.getNextServerFromEureka("ADDRESS", false)
//        return instance.homePageUrl
//    }

//    @RequestMapping("bar")
//    fun bar(): String? {
//        val result = serviceUrl()
//        return result
//    }

//    @RequestMapping("/foo")
//    fun sayJustHello() = WebClient.create()
//        .get()
//        .uri(apiEndpoint)
//        .retrieve()
//        .bodyToMono(String::class.java)
//}


////	@Bean
////	fun hypermediaWebClient(configurer: HypermediaWebClientConfigurer): WebClient.Builder? {
////		return configurer.registerHypermediaTypes(WebClient.builder())
////	}
//
//
//    fun traverson() {
////		val discoverer: LinkDiscoverer = HalLinkDiscoverer()
////		val link: Link = discoverer.findLinkWithRel("foo", content)
//
//        val traverson = Traverson(URI.create(apiEndpoint), MediaTypes.HAL_JSON)
//        val name = traverson
//            .follow("addresses")
//        println(name)
//
//
////		val helloObject: HelloObject = restTemplate.getForObject("http://hello-service/hello", HelloObject::class.java)
//
//
//    }
//
//
//    private val webClient = WebClient.builder()
//        .baseUrl(apiEndpoint)
//        .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
//        .filter(logRequest(logger)).build()
//
//    fun sayHiWithResponsetimeoutAndDebugLog() = WebClient.builder()
//        .baseUrl(apiEndpoint)
//        .filter(logRequest(logger)).build()
//        .get()
//        .httpRequest {
//            it.getNativeRequest<HttpClientRequest>().responseTimeout(Duration.ofSeconds(2))
//        }
//        .retrieve()
//        .bodyToMono(String::class.java)
//
//    fun sayJustHello() = WebClient.create()
//        .get()
//        .uri(apiEndpoint)
//        .retrieve()
//        .bodyToMono(String::class.java)
//
//    fun sayHiWithResponsetimeoutAndCodecLimits() = webClient.get()
//        .httpRequest {
//            it.getNativeRequest<HttpClientRequest>().responseTimeout(Duration.ofSeconds(2))
//        }
//        .retrieve().bodyToMono(String::class.java)
//
//
//}


//
//@FeignClient("spring-cloud-eureka-client")
//interface GreetingClient {
//    @RequestMapping("/greeting")
//    fun greeting(): String?
//}
//
//
//@RestController
//class GreetingController (private val greetingClient: GreetingClient) {
//
//    @RequestMapping("/hello")
//    fun greeting(model: Model): String? {
//        model.addAttribute("greeting", greetingClient.greeting())
//        return "greeting-view"
//    }
//}


//@Configuration
//class MyConfiguration {
//    @Bean
//    @LoadBalanced
//    fun loadBalancedWebClientBuilder(): WebClient.Builder {
//        return WebClient.builder()
//    }
//}
//
//@Service
//class MyClass {
//    @Autowired
//    private val webClientBuilder: WebClient.Builder? = null
//    fun doOtherStuff(): Mono<String> {
//        return webClientBuilder!!.build().get().uri("http://ADDRESS/")
//            .retrieve().bodyToMono(String::class.java)
//    }
//}


//@RestController
//class CallRemoteHttpsServer(
//    private val helloService: HelloService
////    ,private val webClientConfig: WebClientConfig
//    ,private val myClass: MyClass
//) {

//    @Bean
//    @LoadBalanced
//    fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
//        return builder.build()
//    }
//
//    @RequestMapping("/hello")
//    fun hello(): Mono<String> {
//        println("trying .....")
//        val result = myClass.doOtherStuff()
//        return result
//    }
//    @Bean
//    @LoadBalanced
//    fun builder(): WebClient.Builder? {
//        return WebClient.builder()
//    }
//
//    @RequestMapping("/hello")
//    fun hello(): Mono<String>? {
//        return builder()?.build()?.get()?.uri("http://say-hello/")?.retrieve().bodyToMono(String::class.java)
//    }


//    @RequestMapping("/hello")
//    fun hello(): Mono<String>? {
//        return webClientConfig.webClientBuilder()
//            .build().get().uri("http://say-hello/")
//            .retrieve().bodyToMono(String::class.java)
//    }

//    @GetMapping(value = ["foo"])
//    fun foo() = helloService.foo()
//
//    @GetMapping(value = ["/v1"])
//    fun sayHello(): Mono<String> = helloService.sayHiWithResponsetimeoutAndDebugLog()
//
//    @GetMapping(value = ["/v2"])
//    fun sayJustHello() = helloService.sayJustHello()
//
//    @GetMapping(value = ["/v3"])
//    fun sayHiWithResponsetimeoutAndCodecLimits() = helloService.sayHiWithResponsetimeoutAndCodecLimits()
//}


//
//
//@Configuration
//@LoadBalancerClient(name = "say-hello", configuration = [SayHelloConfiguration::class])
//class WebClientConfig {
//    @LoadBalanced
//    @Bean
//    fun webClientBuilder(): WebClient.Builder {
//        return WebClient.builder()
//    }
//}
//
//@Configuration
//class SayHelloConfiguration {
//    @Bean
//    @Primary
//    fun serviceInstanceListSupplier(): ServiceInstanceListSupplier {
//        return DemoServiceInstanceListSuppler("say-hello")
//    }
//
//    internal class DemoServiceInstanceListSuppler(private val serviceId: String) : ServiceInstanceListSupplier {
//        override fun getServiceId(): String {
//            return serviceId
//        }
//
//        override fun get(): Flux<List<ServiceInstance>> {
//            return Flux.just(
//                Arrays
//                    .asList(
//                        DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8081, false)
//                    )
//            )
//        }
//    }
//}


//@Service
//class HelloService(@Value("\${api.endpoint:http://localhost:8081}") private val apiEndpoint: String) {
//
//    private val logger = LoggerFactory.getLogger(javaClass)
//
//
////	internal fun traverseToInternalApiAuftragService(): Traverson {
////		return Traverson(URI.create("http://auftrag-service/internal"),
////			MediaTypes.HAL_JSON).setRestOperations(restTemplate)
////	}
//
////	@Bean
////	fun hypermediaWebClient(configurer: HypermediaWebClientConfigurer): WebClient.Builder? {
////		return configurer.registerHypermediaTypes(WebClient.builder())
////	}
//
//
//    fun traverson() {
////		val discoverer: LinkDiscoverer = HalLinkDiscoverer()
////		val link: Link = discoverer.findLinkWithRel("foo", content)
//
//        val traverson = Traverson(URI.create(apiEndpoint), MediaTypes.HAL_JSON)
//        val name = traverson
//            .follow("addresses")
//        println(name)
//
//
////		val helloObject: HelloObject = restTemplate.getForObject("http://hello-service/hello", HelloObject::class.java)
//
//
//    }
//
//
//    private val webClient = WebClient.builder()
//        .baseUrl(apiEndpoint)
//        .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024) }
//        .filter(logRequest(logger)).build()
//
//    fun sayHiWithResponsetimeoutAndDebugLog() = WebClient.builder()
//        .baseUrl(apiEndpoint)
//        .filter(logRequest(logger)).build()
//        .get()
//        .httpRequest {
//            it.getNativeRequest<HttpClientRequest>().responseTimeout(Duration.ofSeconds(2))
//        }
//        .retrieve()
//        .bodyToMono(String::class.java)
//
//    fun sayJustHello() = WebClient.create()
//        .get()
//        .uri(apiEndpoint)
//        .retrieve()
//        .bodyToMono(String::class.java)
//
//    fun sayHiWithResponsetimeoutAndCodecLimits() = webClient.get()
//        .httpRequest {
//            it.getNativeRequest<HttpClientRequest>().responseTimeout(Duration.ofSeconds(2))
//        }
//        .retrieve().bodyToMono(String::class.java)
//
//
//}
//
//fun logRequest(log: Logger) = ExchangeFilterFunction.ofRequestProcessor {
//    log.debug("Request: {} {}", it.method(), it.url())
//    it.headers().forEach { name, values -> values.forEach { value -> log.debug("{}={}", name, value) } }
//    Mono.just(it)
//}
