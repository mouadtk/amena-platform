package com.opm.app.model.enumeration;

public enum DomainState {
	
	New("New"),Reserved("Reserved"),Used("Used"),Returned("Returned");
	
	
	private String value;
	private DomainState(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
