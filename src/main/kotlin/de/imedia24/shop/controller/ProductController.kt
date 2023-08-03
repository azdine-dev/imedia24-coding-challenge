package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
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
@Api(value = "products", description = "Rest API for Products operations", tags = arrayOf("Products API"))
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    @ApiOperation(value = "Get  Product By Sku", response = ProductResponse::class)
    @ApiResponses(
        ApiResponse(code = 200, message = "Success", response = ProductResponse::class),
        ApiResponse(code = 404, message = "Product not found")
    )
    fun findProductsBySku(
        @ApiParam(value = "sku of the product to retrieve", required = true)
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
    @ApiOperation("Create a new product")
    @ApiResponses(
        ApiResponse(code = 201, message = "Product created", response = ProductResponse::class)
    )
    fun createProduct(
        @ApiParam(value = "Product data to be created", required = true)
        @RequestBody productResponse: ProductResponse
    ) : ResponseEntity<ProductResponse> {
        logger.info("create new  product ${productResponse.toString()}")
        val productDto = productService.createProduct(productResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto!!);
    }
    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    @ApiOperation(value = "Get  Products By List of Sku")
    @ApiResponses(
        ApiResponse(code = 200, message = "Success", response = ProductResponse::class),
    )
    fun getAllProductsBySkus(
        @ApiParam(value = "List of the product skus separated by comma", required = true)
        @RequestParam skus :String
    ) : ResponseEntity<List<ProductResponse?>> {
        logger.info("get products by list of Skus ${skus}");
        val products = productService.getAllProductsBySku(skus);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/products/{sku}")
    @ApiOperation("Update an existing product")
    @ApiResponses(
        ApiResponse(code = 200, message = "Product updated", response = ProductResponse::class),
        ApiResponse(code = 404, message = "Product not found")
    )
    fun updateProductBySku(
        @ApiParam(value = "Sku of the product to be updated", required = true)
        @PathVariable("sku")sku : String,
        @ApiParam(value = "Updated product data", required = true)
        @RequestBody payload: ProductResponse): ResponseEntity<ProductResponse> {
       val product = productService.updateProductBySku(sku, payload)
           ?: return ResponseEntity
                 .notFound().build();
        return ResponseEntity.status(200)
                .body(product)
    }
}
