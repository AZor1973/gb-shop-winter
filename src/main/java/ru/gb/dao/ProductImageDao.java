package ru.gb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gb.entity.ProductImage;

public interface ProductImageDao extends JpaRepository<ProductImage, Long> {

    @Query(value = "SELECT product_image.path FROM product_image WHERE product_image.id = :id LIMIT 1", nativeQuery = true)
    String findImageNameByProductId(@Param("id") Long id);
}
