package com.damao.common.exception.user;

import com.damao.common.exception.BaseException;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
