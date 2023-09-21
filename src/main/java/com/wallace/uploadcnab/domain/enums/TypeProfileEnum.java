package com.wallace.uploadcnab.domain.enums;

import lombok.Getter;

@Getter
public enum TypeProfileEnum {
	ADMIN(2, "ADMIN"), USER(1, "USER");

	private long cod;
	private String desc;

	private TypeProfileEnum(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}
}
