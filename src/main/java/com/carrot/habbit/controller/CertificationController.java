package com.carrot.habbit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carrot.habbit.dto.CertificatePhotoUrlRequestDto;
import com.carrot.habbit.dto.CertificationCreateResponseDto;
import com.carrot.habbit.dto.CertificationFindDaysResponseDto;
import com.carrot.habbit.service.CertificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/habbit/api/v1/certificate")
@RequiredArgsConstructor
public class CertificationController {

	private final CertificationService certificationService;

	@PostMapping("/uploads/{goalId}")
	public ResponseEntity<CertificationCreateResponseDto> uploadFiles(
		@RequestPart(value = "files") List<MultipartFile> multipartFiles, @PathVariable Long goalId) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(certificationService.createCertification(goalId, multipartFiles));
	}

	@PostMapping("/{goalId}")
	public ResponseEntity<CertificationCreateResponseDto> createCert(
		@PathVariable Long goalId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(certificationService.createCertOnlyDate(goalId));
	}

	@GetMapping("/{goalId}/days")
	public ResponseEntity<CertificationFindDaysResponseDto> getDays(@PathVariable Long goalId) {
		return ResponseEntity.ok().body(certificationService.getCertifiedDays(goalId));
	}

	@GetMapping("/find/photoLink")
	public ResponseEntity<String> getPhotoLink(@RequestBody CertificatePhotoUrlRequestDto request) {
		return ResponseEntity.ok().body(certificationService.getPhotoLink(request));
	}
}
