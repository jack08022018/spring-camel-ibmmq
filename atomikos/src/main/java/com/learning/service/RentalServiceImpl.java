package com.learning.service;

import com.learning.repository.mssql.RentalNewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    final RentalNewRepository rentalNewRepository;

    @Override
    @Transactional(
            isolation = Isolation.SERIALIZABLE,
//        transactionManager = "transactionManager",
//        rollbackFor = Exception.class,
//        noRollbackFor = Exception.class,
//        timeout = 60,
            propagation = Propagation.NOT_SUPPORTED
    )
    public void saveRental(int inventoryId) {
        var entity = rentalNewRepository.findById(152).get();
        entity.setInventoryId(inventoryId);
        rentalNewRepository.save(entity);
    }

}
