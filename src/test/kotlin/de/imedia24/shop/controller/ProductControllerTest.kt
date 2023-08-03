package de.imedia24.shop.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.clear
import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import de.imedia24.shop.utils.mapper.ProductMapper
import io.mockk.every
import org.hamcrest.Matchers
import org.hamcrest.Matchers.any
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.hamcrest.Matchers.hasSize
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import java.time.ZonedDateTime


@WebMvcTest
class ProductControllerTest(
    @Autowired val mockMvc : MockMvc,

) {
    lateinit var product1 : ProductResponse
    lateinit var product2 : ProductResponse
    lateinit var updatedproduct : ProductResponse


    @MockkBean
    lateinit var productService: ProductService
    @MockkBean
    lateinit var productRepository : ProductRepository
    @MockkBean
    lateinit var mapper : ProductMapper

    @Autowired
    private lateinit var objectMapper: ObjectMapper




    @BeforeEach
    fun initTests(){
         product1 = ProductResponse("1111","name1",
            "desciption1", "productInfo1",
            123.toBigDecimal());
         product2 = ProductResponse("2222","name2",
            "desciption2", "productInfo2",
            123.toBigDecimal());

        val products: ArrayList<ProductResponse> = ArrayList();
        products.add(product1)
        products.add(product2);

        updatedproduct = ProductResponse("1111","name1Updated",
            "description1Updated", "productInfo1Updated",
            125.toBigDecimal());

        var productEntity1 : ProductEntity = ProductEntity("1111","name1",
            "desciption1", 123.toBigDecimal(), "info",
              ZonedDateTime.now(), ZonedDateTime.now())


        every { productService.findProductBySku("1111") } returns product1;
        every { productService.findProductBySku("2222")} returns product2;
        every { productService.updateProductBySku("1111", any()) } returns updatedproduct;
        every { productService.getAllProductsBySku("1111,2222") } returns products
        every { productService.getAllProductsBySku("3333,4444") } returns ArrayList()

    }





    @Test
    fun givenExistingTwoProductsWhenGetListOfProductsBySkusThenAListIsReturned() {


        mockMvc.perform(get("/products?skus=1111,2222"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$").isArray) // Verify that the content is an array
            .andExpect(jsonPath("$", Matchers.hasSize<ProductResponse>(2))) // Specify the type of the JSON array
            .andExpect(content().json(objectMapper.writeValueAsString(listOf(product1, product2)))) // Compare JSON response with serialized JSON of the expected object
    }

    @Test
    fun givenTwoProductsWhenGetListOfProductsByDifferentSkusThenAnEmptyListIsReturned(){
        mockMvc.perform(get("/products?skus=3333,4444"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$").isArray) // Verify that the content is an array
            .andExpect(jsonPath("$", Matchers.hasSize<ProductResponse>(0))) // Specify the type of the JSON array

    }

    @Test
    fun testWhenUpdateAProductBySkuThenItIsUpdated(){
        // Given
        val sku = "1111"
        val updatedProductJson = """
            {
                "name": "name1Updated",
                "description": "description1Updated",
                "price": 125
            }
        """.trimIndent()

        // Perform the update request using MockMvc
        mockMvc.perform(
            put("/products/{sku}", sku)
                .content(updatedProductJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
    }

    @Test
    fun testWhenUpdatingNonExistingProductThenNotFoundStatusIsReturned(){
        val sku = "4444"
        val updatedProductJson = """
            {
                "name": "name1Updated",
                "description": "description1Updated",
                "price": 125
            }
        """.trimIndent()

        every { productService.updateProductBySku(sku, any()) } returns null

        // Perform the update request using MockMvc
        mockMvc.perform(
            put("/products/{sku}", sku)
                .content(updatedProductJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound)
    }

}