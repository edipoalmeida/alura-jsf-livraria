package com.ealmeida.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ealmeida.livraria.dao.UsuarioDao;
import com.ealmeida.livraria.modelo.Usuario;
import com.ealmeida.livraria.util.ForwardView;

@Named
@ViewScoped // javax.faces.view.ViewScoped
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();

	@Inject
	UsuarioDao usuarioDao;
	
	@Inject
	FacesContext context;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public ForwardView efetuaLogin() {
		System.out.println("Fazendo login do usuário " + this.usuario.getEmail());

		boolean existe = usuarioDao.existe(this.usuario);
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new ForwardView("livro");
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		return null;
	}

	public ForwardView deslogar() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return new ForwardView("login");
	}
}