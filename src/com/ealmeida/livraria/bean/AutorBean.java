package com.ealmeida.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ealmeida.livraria.dao.AutorDao;
import com.ealmeida.livraria.modelo.Autor;
import com.ealmeida.livraria.tx.Transacional;
import com.ealmeida.livraria.util.ForwardView;

@Named
@ViewScoped // javax.faces.view.ViewScoped
public class AutorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDao dao;

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Autor getAutor() {
		return autor;
	}

	public List<Autor> getAutores() {
		return dao.listaTodos();
	}

	public void carregaPelaId() {
		Integer id = this.autor.getId();
		this.autor = dao.buscaPorId(id);
		if (this.autor == null) {
			this.autor = new Autor();
		}
	}

	@Transacional
	public ForwardView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null)
			dao.adiciona(this.autor);
		else
			dao.atualiza(this.autor);

		this.autor = new Autor();

		return new ForwardView("livro");
	}

	public void carregar(Autor autor) {
		System.out.println("Carregando autor");
		this.autor = autor;
	}

	@Transacional
	public void remover(Autor autor) {
		System.out.println("Removendo autor");
		dao.remove(autor);
	}
}
