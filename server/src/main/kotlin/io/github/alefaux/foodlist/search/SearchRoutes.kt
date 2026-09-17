package io.github.alefaux.foodlist.search

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.searchRoutes(
    searchService: SearchService
) {
    get("/search") {
        val query = call.request.queryParameters["query"]
        val results = searchService.search(query)
        call.respond(HttpStatusCode.OK,results)
    }
}