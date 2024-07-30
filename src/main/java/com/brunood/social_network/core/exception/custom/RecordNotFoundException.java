package com.brunood.social_network.core.exception.custom;

public class RecordNotFoundException extends BusinessException {

    private static final long serialVersionUID = -3585414845239378650L;

    public RecordNotFoundException() {
        super("002-NAO-ENCONTRADO", "Registro não encontrado!");
    }

    public RecordNotFoundException(String code, String message) {
        super(code, message);
    }

}
