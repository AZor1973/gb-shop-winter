package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.gb.dao.ProductImageDao;
import ru.gb.exceptions.ProductImageNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageDao productImageDao;

    public String getImageNameByProductId(Long id) {
        return productImageDao.findImageNameByProductId(id);
    }

    public Integer imageQuantityByProductId(Long id){
        return productImageDao.imageQuantityByProductId(id);
    }

    public BufferedImage loadAllFilesAsResource(Long id, Integer i) throws IOException {
        List<BufferedImage> images = new ArrayList<>();
        List<ClassPathResource> resources = productImageDao.findAllImageNamesByProductId(id)
                .stream().map(s -> new ClassPathResource("/static/images/" + s))
                .collect(Collectors.toList());
        for (ClassPathResource resource : resources) {
            if (resource.exists()) {
                images.add(ImageIO.read(resource.getFile()));
            } else {
                log.error("Image with name {} not found!", resource.getFilename());
                throw new ProductImageNotFoundException(resource.getFilename());
            }
        }
        return images.get(i);
    }

    public BufferedImage loadFileAsResource(Long id) throws IOException {
        String imageName = productImageDao.findImageNameByProductId(id);
        ClassPathResource image = new ClassPathResource("/static/images/" + imageName);
        if (image.exists()) {
            return ImageIO.read(image.getFile());
        } else {
            log.error("Image with name {} not found!", imageName);
            // todo ДЗ - сделать наслденика FileNotFoundException -> ProductImageNotFoundException
            throw new ProductImageNotFoundException(imageName);
        }
    }
}
