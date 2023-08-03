package de.imedia24.shop.db.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.PrePersist
import javax.persistence.Table


@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "sku", nullable = true)
    var sku: String?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @Column(name = "productInfo", nullable = true)
    val productInfo : String?,

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime


){
    @PrePersist
    fun initializeSkuLenght() {
        if(sku !=null){
            sku = sku!!.substring(0,8);
        }else{
           sku = UUID.randomUUID().toString().substring(0,8);
        }
    }

}
