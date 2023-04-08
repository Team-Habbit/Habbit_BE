package com.carrot.habbit.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrot.habbit.domain.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);
}
