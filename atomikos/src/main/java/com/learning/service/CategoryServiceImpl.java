package com.learning.service;

import com.learning.repository.mymsdb.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void saveCategory(String name) {
        var entity = categoryRepository.findById(52).get();
        entity.setName(name);
        categoryRepository.save(entity);
    }

}
