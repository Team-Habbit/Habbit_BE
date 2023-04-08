package com.carrot.habbit.exception.certificate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.carrot.habbit.controller.CertificationController;
import com.carrot.habbit.exception.ErrorMessage;

@RestControllerAdvice(basePackageClasses = CertificationController.class)
public class CertificateExceptionHandler {

	@ExceptionHandler(NotFoundGoalException.class)
	public ResponseEntity<ErrorMessage> notFoundGoalException(NotFoundGoalException e) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(e, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotFoundCertificationException.class)
	public ResponseEntity<ErrorMessage> notFoundCertificationException(NotFoundCertificationException e) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(e, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(AlreadyCertificationException.class)
	public ResponseEntity<ErrorMessage> alreadyCertificationException(AlreadyCertificationException e) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(e, HttpStatus.BAD_REQUEST));
	}
}
