package com.ealmeida.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ealmeida.livraria.dao.AutorDao;
import com.ealmeida.livraria.dao.LivroDao;
import com.ealmeida.livraria.modelo.Autor;
import com.ealmeida.livraria.modelo.Livro;
import com.ealmeida.livraria.tx.Transacional;
import com.ealmeida.livraria.util.ForwardView;

@Named
@ViewScoped // javax.faces.view.ViewScoped
public class LivroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	private Integer livroId;

	private List<Livro> livros;

	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	@Inject
	private LivroDao dao;

	@Inject
	private AutorDao autorDao;

	@Inject
	FacesContext context;

	public List<String> getGeneros() {
		return generos;
	}

	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}

	public void carregaPelaId() {
		this.livro = this.dao.buscaPorId(this.livroId);
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = this.dao.listaTodos();
		}
		return livros;
	}

	public void gravarAutor() {
		System.out.println("Adicionando Autor");
		this.livro.adicionaAutor(autorDao.buscaPorId(this.autorId));
		for (Autor autor : this.livro.getAutores()) {
			System.out.println(autor.getNome());
		}
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			context.addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			System.out.println("Atualizando Livro");
			dao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	@Transacional
	public void alterar(Livro livro) {
		this.livro = this.dao.buscaPorId(livro.getId());
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		dao.remove(livro);
		this.livros = dao.listaTodos();
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removerAutor(autor);
	}

	public ForwardView formAutor() {
		System.out.println("Chamando o formulário do Autor");
		return new ForwardView("autor");
	}

	public void deveComecarComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		if (!value.toString().startsWith("1")) {
			throw new ValidatorException(new FacesMessage("Deveria começar com 1"));
		}
	}
}
