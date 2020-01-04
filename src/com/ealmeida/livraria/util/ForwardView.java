package com.ealmeida.livraria.util;

public class ForwardView {

	private final String viewName;

	public ForwardView(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}

}
