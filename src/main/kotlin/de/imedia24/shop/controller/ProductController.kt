package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.websocket.server.PathParam

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @PostMapping("/products", produces = ["application/json;charset=utf-8"])
    fun createProduct(@RequestBody productResponse: ProductResponse) : ResponseEntity<ProductResponse> {
        logger.info("create new  product ${productResponse.toString()}")
        val productDto = productService.createProduct(productResponse);
        return ResponseEntity.ok(productDto!!);
    }
    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun getAllProductsBySkus(@RequestParam skus :String) : ResponseEntity<List<ProductResponse?>> {
        logger.info("get products by list of Skus ${skus}");
        val products = productService.getAllProductsBySku(skus);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/products/{sku}")
    fun updateProductBySku(@PathVariable("sku")sku : String, @RequestBody payload: ProductResponse): ResponseEntity<ProductResponse> {
       val product = productService.updateProductBySku(sku, payload)
           ?: return ResponseEntity
                 .notFound().build();
        return ResponseEntity.status(200)
                .body(product)
    }
}
