package de.imedia24.shop.service

import de.imedia24.shop.domain.product.ProductResponse

interface IProductService {
    fun findProductBySku(sku: String): ProductResponse?
    fun createProduct(productDto : ProductResponse) : ProductResponse?
}