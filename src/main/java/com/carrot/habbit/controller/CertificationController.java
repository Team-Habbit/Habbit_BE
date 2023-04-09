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
import com.carrot.habbit.service.CertificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/habbit/api/v1/certificate")
@RequiredArgsConstructor
public class CertificationController {

	private final CertificationService certificationService;

	@PostMapping("/uploads/{goalId}")
	public ResponseEntity uploadFiles(
		@RequestPart(value = "files") List<MultipartFile> multipartFiles, @PathVariable Long goalId) {
		certificationService.createCertification(goalId, multipartFiles);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PostMapping("/{goalId}")
	public ResponseEntity createCert(
		@PathVariable Long goalId) {
		certificationService.createCertOnlyDate(goalId);
		return new ResponseEntity(HttpStatus.CREATED);
	}

	@GetMapping("/{goalId}/days")
	public ResponseEntity<List<String>> getDays(@PathVariable Long goalId) {
		List<String> certifiedDays = certificationService.getCertifiedDays(goalId);
		return ResponseEntity.ok().body(certifiedDays);
	}

	@GetMapping("/find/photoLink")
	public ResponseEntity<String> getPhotoLink(@RequestBody CertificatePhotoUrlRequestDto request) {
		return ResponseEntity.ok().body(certificationService.getPhotoLink(request));
	}
}
