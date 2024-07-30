package com.brunood.social_network.core.exception.error;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StandardError {

	private String code;
	private String description;
}