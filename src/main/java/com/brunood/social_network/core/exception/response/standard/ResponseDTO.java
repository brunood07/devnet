package com.brunood.social_network.core.exception.response.standard;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

        private LocalDateTime date;

        private String message;

        private String detail;

        private ResponseType responseType;

        private Integer codeStatus;

        private String reasonPhraseStatus;

        private Object data;
}