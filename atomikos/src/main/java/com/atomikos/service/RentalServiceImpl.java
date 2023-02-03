package com.atomikos.service;

import com.atomikos.enumerator.Status;
import com.atomikos.repository.mariaDB.CityRepository;
import com.atomikos.repository.mariaDB.entity.CityEntity;
import com.atomikos.repository.mssql.RentalNewRepository;
import com.atomikos.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    final RentalNewRepository rentalNewRepository;

    @Override
    @Transactional
    public void saveRental(int inventoryId) {
        RentalNewEntity entity = rentalNewRepository.findById(152).get();
        entity.setInventoryId(inventoryId);
        rentalNewRepository.save(entity);
    }
}
