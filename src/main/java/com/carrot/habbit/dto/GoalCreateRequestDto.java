package com.carrot.habbit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GoalCreateRequestDto {

	private String goalName;

	private String categoryName;

	private String startDate;

	private String endDate;
}
