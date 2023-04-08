package com.carrot.habbit.domain.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.carrot.habbit.dto.GoalCreateRequestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Goal extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Category category;

	private String goalName;

	private LocalDate startDate;

	private LocalDate endDate;

	private int goalCount;

	private int currentCount;

	private double goalPercent;

	public static Goal of(Category category, LocalDate startDate, LocalDate endDate, int betweenDays, GoalCreateRequestDto request) {
		return Goal.builder()
			.category(category)
			.goalName(request.getGoalName())
			.startDate(startDate)
			.endDate(endDate)
			.goalCount(betweenDays)
			.currentCount(0)
			.goalPercent(0)
			.build();
	}
}
