package com.carrot.habbit.exception.goal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.carrot.habbit.controller.GoalController;
import com.carrot.habbit.exception.ErrorMessage;

@RestControllerAdvice(basePackageClasses = GoalController.class)
public class GoalExceptionHandler {

	@ExceptionHandler(NotFoundCategoryException.class)
	public ResponseEntity<ErrorMessage> notFoundCategoryException(NotFoundCategoryException e) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(e, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotFoundGoalException.class)
	public ResponseEntity<ErrorMessage> notFoundGoalException(NotFoundGoalException e) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(e, HttpStatus.BAD_REQUEST));
	}
}
