package com.brunood.social_network.core.exception.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
public class BusinessException extends RuntimeException {

	private String code;
	private String message;

	public BusinessException() {
		super();
	}
}
