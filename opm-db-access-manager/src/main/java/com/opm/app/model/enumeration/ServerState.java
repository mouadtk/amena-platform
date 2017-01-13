package com.opm.app.model.enumeration;

public enum ServerState {
	
	New("New"),PreProd("PreProd"),Production("Production"),Returned("Returned"),Down("Down");	
	
	private String value;
	private ServerState(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return this.value;
	}
}
