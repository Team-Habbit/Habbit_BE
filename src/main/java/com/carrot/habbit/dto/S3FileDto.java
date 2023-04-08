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
public class S3FileDto {

	private String originalFileName;
	private String uploadFileName;
	private String uploadFilePath;
	private String uploadFileUrl;
}
