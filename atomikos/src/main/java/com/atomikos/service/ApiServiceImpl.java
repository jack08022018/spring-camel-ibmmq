package com.atomikos.service;

import com.atomikos.dto.User;
import com.atomikos.repository.mariaDB.ActorRepository;
import com.atomikos.repository.mssql.RentalNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiServiceImpl implements ApiService {
//    final ActorService actorService;
//    final CityService cityService;
    final RentalNewRepository rentalNewRepository;
    final ActorRepository actorRepository;
    final CityService cityService;
    final RentalService rentalService;

    @Override
    @Transactional
    public <T> T handleTransactional(User user) {
//        String postfix = " 13";
//        actorService.saveActor("THORA" + postfix);
//        cityService.saveCity("Ziguinchor" + postfix);

        int inventoryId = 11;
        cityService.saveCity("Ziguinchor " + inventoryId);
        rentalService.saveRental(inventoryId);
//        int a = 1/0;
        return (T) "success";
    }

    @Override
    public <T> T findAllRental() {
        return (T) rentalNewRepository.findAll();
    }

    @Override
    public <T> T getActor() {
        var data = actorRepository.findTop2ByLastName();
        return (T) data;
    }

}
