package com.carrot.habbit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrot.habbit.dto.GoalCreateRequestDto;
import com.carrot.habbit.dto.GoalFindResponseDto;
import com.carrot.habbit.service.GoalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/habbit/api/v1/goal")
@RequiredArgsConstructor
public class GoalController {

	private final GoalService goalService;

	@PostMapping
	public ResponseEntity createGoal(
		@RequestBody GoalCreateRequestDto request
	) {
		goalService.createGoal(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/name/{goalId}")
	public ResponseEntity<GoalFindResponseDto> getGoalName(
		@PathVariable Long goalId
	) {
		return ResponseEntity.ok().body(goalService.findGoal(goalId));
	}
}
