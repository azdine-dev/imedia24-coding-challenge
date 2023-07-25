package de.imedia24.shop.utils.mapper

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.domain.product.ProductResponse
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ProductMapper : Mapper<ProductResponse, ProductEntity> {

    override fun fromEntity(entity: ProductEntity?): ProductResponse? {
        if(entity == null){
            return null;
        }
        return ProductResponse(
            entity.sku, entity.name, entity.description!!,
            entity.price);
    }

    override fun toEntity(domain: ProductResponse): ProductEntity {
        var createdAt: ZonedDateTime = ZonedDateTime.now();
        var updatedAt: ZonedDateTime = ZonedDateTime.now();

            return ProductEntity(
                domain.sku, domain.name, domain.description,
                domain.price, createdAt, updatedAt
            )

    }

}
