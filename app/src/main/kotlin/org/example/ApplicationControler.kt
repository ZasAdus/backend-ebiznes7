package org.example

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["http://localhost:3000"])
class ApplicationControler(
    private val productRepository: ProductRepository
) {

    @GetMapping("/products")
    fun getProducts(): List<Product> {
        return productRepository.findAll()
    }


    @PostMapping("/payments")
    fun buy(@RequestBody request: BuyRequest): Product {
        val product = productRepository.findById(request.productId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found") }

        if (product.quantity < request.quantity) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough stock")
        }

        product.quantity -= request.quantity

        return productRepository.save(product)
    }

    data class BuyRequest(
        val productId: Long,
        val quantity: Int
    )
}
