package com.brunood.social_network.core.exception.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class GeneralException extends BusinessException {

	private static final long serialVersionUID = -3866243421121379230L;

	@Autowired
	private MessageSource messageSource;

	public GeneralException() {
		super("001-ERRO-GERAL", "Problema ao processar sua solicitação!");
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
