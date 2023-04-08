package com.carrot.habbit.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrot.habbit.domain.model.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
