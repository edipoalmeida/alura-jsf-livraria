package com.ealmeida.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ealmeida.livraria.dao.UsuarioDao;
import com.ealmeida.livraria.modelo.Usuario;
import com.ealmeida.livraria.util.ForwardView;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}

	public ForwardView efetuaLogin() {
		System.out.println("Fazendo login do usuário " + this.usuario.getEmail());

		boolean existe = new UsuarioDao().existe(this.usuario);
		FacesContext context = FacesContext.getCurrentInstance();
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new ForwardView("livro");
		}

		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		return null;
	}

	public ForwardView deslogar() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return new ForwardView("login");
	}
}