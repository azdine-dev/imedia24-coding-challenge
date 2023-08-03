package de.imedia24.shop.db.repository

import de.imedia24.shop.db.entity.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ProductRepository : CrudRepository<ProductEntity, String> {

    fun findBySku(sku: String): Optional<ProductEntity>?

    fun findBySkuStartingWith(sku : String) : ProductEntity?
    fun findBySkuIn(skus :List<String>) : List<ProductEntity?>

    fun existsBySku(sku: String) : Boolean
}