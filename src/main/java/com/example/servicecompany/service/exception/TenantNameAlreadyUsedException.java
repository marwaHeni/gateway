package com.example.servicecompany.service.exception;

public class TenantNameAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TenantNameAlreadyUsedException() {
        super("Tenant name already used!");
    }

}
