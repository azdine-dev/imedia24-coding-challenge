package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.exception.ProductNotFoundException
import de.imedia24.shop.utils.mapper.ProductMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper) : IProductService {

    override fun findProductBySku(sku: String): ProductResponse? {
         var allProducts = productRepository.findAll();
         var product = productRepository.findBySku(sku);
         var productDto = product?.let {
            if(it.isPresent) productMapper.fromEntity(it.get()) else null;
         };
      return  productDto;
    }

    override fun createProduct(productDto : ProductResponse) : ProductResponse? {
        var product = productMapper.toEntity(productDto)
        productRepository.save(product);
        return productMapper.fromEntity(product);
    }

    override fun getAllProductsBySku(skus: String): List<ProductResponse?> {
        var skusArray = skus.split(",");
        var productsBySkus = productRepository.findBySkuIn(skusArray);

        return productsBySkus.map { productEntity -> productMapper.fromEntity(productEntity) }
    }


    override fun updateProductBySku(sku: String, productDto : ProductResponse): ProductResponse? {
       if(productRepository.findBySku(sku)?.isPresent!!){
           var productEntity = productMapper.toEntity(productDto);
           productEntity.sku = sku;
           var updatedProductEntity = productRepository.save(productEntity);
           return  productMapper.fromEntity(updatedProductEntity);
        }else {
            return null
        }
    }




}
