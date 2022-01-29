package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.dao.ManufacturerDao;
import ru.gb.entity.Manufacturer;
import ru.gb.web.dto.mapper.ManufacturerMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerService {

    private final ManufacturerDao manufacturerDao;
    private final ManufacturerMapper manufacturerMapper;


    @Transactional
    public ManufacturerDto save(final ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerMapper.toManufacturer(manufacturerDto);
        if (manufacturer.getId() != null) {
            manufacturerDao.findById(manufacturer.getId()).ifPresent(
                    (p) -> manufacturer.setVersion(p.getVersion())
            );
        }
        return manufacturerMapper.toManufacturerDto(manufacturerDao.save(manufacturer));
    }

    @Transactional(readOnly = true)
    public ManufacturerDto findById(Long id) {
        return manufacturerMapper.toManufacturerDto(manufacturerDao.findById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    public ManufacturerDto findByName(String name) {
        return manufacturerMapper.toManufacturerDto(manufacturerDao.findByName(name).orElse(null));
    }

    public List<ManufacturerDto> findAll() {
        return manufacturerDao.findAll().stream().map(manufacturerMapper::toManufacturerDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            manufacturerDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
}
