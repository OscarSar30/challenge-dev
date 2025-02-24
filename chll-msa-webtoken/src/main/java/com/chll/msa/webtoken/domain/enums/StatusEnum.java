package com.chll.msa.webtoken.domain.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    StatusEnum (String value) {
        this.value = value;
    }

}
