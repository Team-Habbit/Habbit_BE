package com.carrot.habbit.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.carrot.habbit.domain.model.Category;
import com.carrot.habbit.domain.model.Goal;
import com.carrot.habbit.domain.repository.CategoryRepository;
import com.carrot.habbit.domain.repository.GoalRepository;
import com.carrot.habbit.domain.repository.MessageRepository;
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

	private LocalDate stringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	public GoalFindResponseDto findGoal(Long goalId) {
		Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new NotFoundGoalException("목표 정보를 찾을 수 없습니다."));
		return GoalFindResponseDto.builder()
			.goalName(goal.getGoalName())
			.build();
	}
}
