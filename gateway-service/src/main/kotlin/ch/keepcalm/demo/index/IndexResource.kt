package ch.keepcalm.demo.index

import org.springframework.cloud.gateway.route.Route
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@RestController
@RequestMapping(produces = [MediaTypes.HAL_JSON_VALUE])
class IndexResource(val routeLocator: RouteLocator) {

    @GetMapping(value = ["/myapp/services"])
    fun index(serverWebExchange: ServerWebExchange): Mono<EntityModel<Unit>> {
        val serviceLinks = routeLocator.routes
            .flatMap { route: Route ->
                linkTo(methodOn(IndexResource::class.java).index(serverWebExchange)).slash(route.uri.path).slash(route.metadata["relName"].toString()).withRel(route.metadata["relName"].toString()).toMono()
            }
        return Mono.empty<Link>().toFlux()
            .concatWith(linkTo(methodOn(IndexResource::class.java).index(serverWebExchange)).withSelfRel().toMono().toFlux())
            .concatWith(serviceLinks)
            .collectMap { link -> link.rel.toString() }
            .map { linkMap -> linkMap.values }
            .map { link ->
                EntityModel.of(Unit, link)
            }
    }
}
