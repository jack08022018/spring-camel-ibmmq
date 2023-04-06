package com.learning.service;

import com.learning.repository.mariaDB.CityRepository;
import com.learning.repository.mssql.RentalNewRepository;
import com.learning.repository.mymsdb.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiServiceImpl implements ApiService {
    final RentalNewRepository rentalNewRepository;
    final CategoryRepository categoryRepository;
    final CityService cityService;
    final RentalService rentalService;
    final CategoryService categoryService;
    final CityRepository cityRepository;

    @Override
    @Transactional
    public <T> T handleTransactional() {
//        String postfix = " 13";
//        actorService.saveActor("THORA" + postfix);
//        cityService.saveCity("Ziguinchor" + postfix);
//        var entity = CategoryEntity.builder()
//                .name("Category")
//                .lastUpdate(LocalDateTime.now())
//                .build();
//        categoryRepository.save(entity);

        int inventoryId = 20;
//        rentalService.saveRental(inventoryId);
//        categoryService.saveCategory("Category " + inventoryId);
        cityService.saveCity("Ziguinchor" + inventoryId);
//        int a = 1/0;
        return (T) "success";
    }

    @Override
    public <T> T findAllRental() {
        return (T) rentalNewRepository.findAll();
    }

    @Override
    public <T> T getDataTransaction() {
        ModelMap result = new ModelMap();
        var category = categoryRepository.findById(52).get();
        var rental = rentalNewRepository.findById(152).get();
        var city = cityRepository.findById(600).get();
        result.put("category mymsdb", category);
        result.put("rental mssql", rental);
        result.put("city mariadb", city);
        return (T) result;
    }

}
