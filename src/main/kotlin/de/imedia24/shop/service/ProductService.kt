package de.imedia24.shop.service

import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.utils.mapper.ProductMapper
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper) : IProductService {

    override fun findProductBySku(sku: String): ProductResponse? {
         var product = productRepository.findBySku(sku);
         var productDto = product?.let { productMapper.fromEntity(it) };
      return  productDto;
    }

    override fun createProduct(productDto : ProductResponse) : ProductResponse? {
        var product = productMapper.toEntity(productDto)
        productRepository.save(product);
        return productMapper.fromEntity(product);
    }


}
