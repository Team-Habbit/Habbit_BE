package com.carrot.habbit.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrot.habbit.domain.model.Certification;
import com.carrot.habbit.domain.model.Goal;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
	List<Certification> findAllByGoal(Goal goal);

	Optional<Certification> findBySubmissionDate(LocalDate targetDate);

	List<Certification> findByGoalAndSubmissionDateBetween(Goal goal, LocalDate startDate, LocalDate endDate);
}
