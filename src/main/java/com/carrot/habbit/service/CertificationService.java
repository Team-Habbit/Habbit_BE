package com.carrot.habbit.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carrot.habbit.domain.model.Certification;
import com.carrot.habbit.domain.model.Goal;
import com.carrot.habbit.domain.repository.CertificationRepository;
import com.carrot.habbit.dto.CertificatePhotoUrlRequestDto;
import com.carrot.habbit.dto.CertificationCreateResponseDto;
import com.carrot.habbit.dto.CertificationFindDaysResponseDto;
import com.carrot.habbit.dto.S3FileDto;
import com.carrot.habbit.exception.certificate.AlreadyCertificationException;
import com.carrot.habbit.exception.certificate.NotFoundCertificationException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificationService {

	private final GoalService goalService;
	private final CertificationRepository certificationRepository;
	private final FileUploadService fileUploadService;

	@Transactional
	public CertificationCreateResponseDto createCertification(Long goalId, List<MultipartFile> multipartFiles) {
		LocalDate now = LocalDate.now();
		Optional<Certification> optionalCert = certificationRepository.findBySubmissionDate(now);
		if (optionalCert.isPresent()) {
			throw new AlreadyCertificationException("이미 등록한 목표가 존재합니다.");
		}
		Goal goal = goalService.findById(goalId);

		List<S3FileDto> s3FileDtos = fileUploadService.uploadFiles(goal.getGoalName(), multipartFiles);
		String uploadFileUrl = s3FileDtos.stream().findFirst().get().getUploadFileUrl();

		Certification certification = Certification.of(goal, uploadFileUrl);

		goal.setCurrentCount(goal.getCurrentCount() + 1);
		double newPercent = Math.round((double)goal.getCurrentCount() / goal.getGoalCount() * 100) / 100.0;
		goal.setGoalPercent(newPercent);

		certificationRepository.save(certification);
		return CertificationCreateResponseDto.builder()
			.certificationId(certification.getId())
			.build();
	}

	@Transactional
	public CertificationCreateResponseDto createCertOnlyDate(Long goalId) {
		LocalDate now = LocalDate.now();
		Optional<Certification> optionalCert = certificationRepository.findBySubmissionDate(now);
		if (optionalCert.isPresent()) {
			throw new AlreadyCertificationException("이미 등록한 목표가 존재합니다.");
		}
		Goal goal = goalService.findById(goalId);
		Certification certification = Certification.of(goal, null);

		goal.setCurrentCount(goal.getCurrentCount() + 1);
		double newPercent = Math.round((double)goal.getCurrentCount() / goal.getGoalCount() * 100) / 100.0;
		goal.setGoalPercent(newPercent);

		certificationRepository.save(certification);
		return CertificationCreateResponseDto.builder()
			.certificationId(certification.getId())
			.build();
	}

	public CertificationFindDaysResponseDto getCertifiedDays(Long goalId) {
		Goal goal = goalService.findById(goalId);
		List<Certification> certifications = certificationRepository.findAllByGoal(goal);
		return CertificationFindDaysResponseDto.builder()
			.certDays(
				certifications
					.stream()
					.map(i -> localDateToString(i.getSubmissionDate()))
					.collect(Collectors.toList())
			)
			.build();
	}

	public String getPhotoLink(CertificatePhotoUrlRequestDto request) {
		Certification certification =
			certificationRepository.findBySubmissionDate(stringToLocalDate(request.getTargetDate()))
				.orElseThrow(() -> new NotFoundCertificationException("인증 정보를 찾을 수 없습니다."));
		return certification.getPhotoUrl();
	}

	private String localDateToString(LocalDate date) {
		return date.format(DateTimeFormatter.ISO_DATE);
	}

	private LocalDate stringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}
}
