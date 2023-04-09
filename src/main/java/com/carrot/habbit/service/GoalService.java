package com.carrot.habbit.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carrot.habbit.domain.model.Category;
import com.carrot.habbit.domain.model.Certification;
import com.carrot.habbit.domain.model.Goal;
import com.carrot.habbit.domain.repository.CategoryRepository;
import com.carrot.habbit.domain.repository.CertificationRepository;
import com.carrot.habbit.domain.repository.GoalRepository;
import com.carrot.habbit.dto.GoalCreateRequestDto;
import com.carrot.habbit.dto.GoalFindResponseDto;
import com.carrot.habbit.exception.goal.NotFoundCategoryException;
import com.carrot.habbit.exception.goal.NotFoundGoalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoalService {

	private final GoalRepository goalRepository;
	private final CategoryRepository categoryRepository;
	private final CertificationRepository certificationRepository;

	public void createGoal(GoalCreateRequestDto request) {
		Category category = categoryRepository.findByCategoryName(request.getCategoryName())
			.orElseThrow(() -> new NotFoundCategoryException("카테고리 정보를 찾을 수 없습니다."));

		LocalDate startDate = stringToLocalDate(request.getStartDate());
		LocalDate endDate = stringToLocalDate(request.getEndDate());

		LocalDateTime date1 = startDate.atStartOfDay();
		LocalDateTime date2 = endDate.atStartOfDay();
		int betweenDays = (int)Duration.between(date1, date2).toDays();

		Goal goal = Goal.of(category, startDate, endDate, betweenDays, request);

		goalRepository.save(goal);
	}

	public GoalFindResponseDto findGoal(Long goalId) {
		Goal goal = findById(goalId);
		Optional<Certification> optionalCert = certificationRepository.findBySubmissionDate(LocalDate.now());

		if (optionalCert.isPresent()) {
			return GoalFindResponseDto.builder()
				.goalName(goal.getGoalName())
				.goalPercent(goal.getGoalPercent())
				.weekCount(findByNowDateBetweenAWeek(goal))
				.isCompleted(true)
				.build();
		}

		return GoalFindResponseDto.builder()
			.goalName(goal.getGoalName())
			.goalPercent(goal.getGoalPercent())
			.weekCount(findByNowDateBetweenAWeek(goal))
			.isCompleted(false)
			.build();
	}

	public Goal findById(Long goalId) {
		return goalRepository.findById(goalId).orElseThrow(() -> new NotFoundGoalException("목표 정보를 찾을 수 없습니다."));
	}

	private int findByNowDateBetweenAWeek(Goal goal) {
		LocalDate nowDate = LocalDate.now();
		return certificationRepository.findByGoalAndSubmissionDateBetween(
			goal, nowDate.minusWeeks(1), nowDate).size();
	}

	private LocalDate stringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}
}
