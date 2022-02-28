package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.gb.dao.ProductImageDao;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageDao productImageDao;

    public BufferedImage loadFileAsResource(Long id) throws IOException {
        String imageName = productImageDao.findImageNameByProductId(id);
        ClassPathResource image = new ClassPathResource("/static/images/" + imageName);
        if (image.exists()) {
            return ImageIO.read(image.getFile());
        } else {
            log.error("Image with name {} not found!", imageName);
            // todo ДЗ - сделать наслденика FileNotFoundException -> ProductImageNotFoundException
            throw new FileNotFoundException("File " + imageName + " not found");
        }
    }
}
