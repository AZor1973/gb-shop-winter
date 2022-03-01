package ru.gb.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;
import ru.gb.entity.Product;
import ru.gb.web.dto.ProductManufacturerDto;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {ManufacturerMapper.class, CategoryMapper.class})
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    @Mapping(source = "manufacturer", target = "manufacturerDto")
    ProductManufacturerDto toProductManufacturerDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(
                () -> new NoSuchElementException("There isn't manufacturer with name " + manufacturer)
        );
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    // todo ДЗ - если что поменять здесь маппинг на list of ids to set of category
//    default Set<Category> categoryDtoSetToCategorySet(Set<CategoryDto> categories, @Context CategoryDao categoryDao) {
//        return categories.stream().map(c -> categoryDao.findByTitleAndId(c.getTitle(), c.getCategoryId())
//                        .orElseThrow(
//                                () -> new NoSuchElementException("There isn't category with name " + c.getTitle())
//                        ))
//                .collect(Collectors.toSet());
//    }

    default Set<Category> getCategories(Set<String> categories, @Context CategoryDao categoryDao) {
        return categories.stream().map(s -> categoryDao.findByTitle(s).orElseThrow(
                        () -> new NoSuchElementException("There isn't category with name " + s)
                ))
                .collect(Collectors.toSet());
    }

    default Set<String> getCategories (Set<Category> categories){
        return categories.stream()
                .map(Category::getTitle).collect(Collectors.toSet());
    }
}
