package com.opm.app.model.enumeration;

public enum DomainProviderName {

	NameCheap("NameCheap");
	
	private String value;
	
	private DomainProviderName(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
