package com.opm.app.model.enumeration;

public enum InstallationStatus {

	WAITING("WAITING"),INPROCESS("INPROCESS"),
	FAILED("FAILED"),DONE("DONE");
	
	private String value;
	private InstallationStatus(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return this.value;
	}
}
