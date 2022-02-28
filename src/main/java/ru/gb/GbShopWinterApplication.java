package ru.gb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.entity.Manufacturer;
import ru.gb.service.CategoryService;
import ru.gb.service.ManufacturerService;

@SpringBootApplication
public class GbShopWinterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbShopWinterApplication.class, args);
    }

    @Bean
    public CommandLineRunner bootstrap(CategoryService categoryService, ManufacturerService manufacturerService) {
        return (args) -> {
            // todo ДЗ сохранить категорию
            // todo ДЗ сохранить производителя
            // todo потом закомментировать этот блок
        };
    }
}
