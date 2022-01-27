package ru.gb.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;
import ru.gb.entity.Product;
import ru.gb.web.dto.ProductDto;
import ru.gb.web.dto.ProductManufacturerDto;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    @Mapping(source = "manufacturer", target = "manufacturerDto")
    ProductManufacturerDto toProductManufacturerDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Set<Category> getCategories(Set<String> categories, @Context CategoryDao categoryDao) {
        return categories.stream().map(c -> categoryDao.findByTitle(c).orElseThrow(NoSuchElementException::new)).collect(Collectors.toSet());
    }

    default Set<String> getCategories(Set<Category> categories) {
        return categories.stream().map(Category::getTitle).collect(Collectors.toSet());
    }

}
