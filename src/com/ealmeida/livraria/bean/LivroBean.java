package com.ealmeida.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.ealmeida.livraria.dao.DAO;
import com.ealmeida.livraria.modelo.Autor;
import com.ealmeida.livraria.modelo.Livro;
import com.ealmeida.livraria.util.ForwardView;

@SuppressWarnings("deprecation")
@ManagedBean
@ViewScoped
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
		this.livro = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		return livros;
	}

	public void gravarAutor() {
		System.out.println("Adicionando Autor");
		this.livro.adicionaAutor(new DAO<Autor>(Autor.class).buscaPorId(this.autorId));
		for (Autor autor : this.livro.getAutores()) {
			System.out.println(autor.getNome());
		}
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			System.out.println("Atualizando Livro");
			dao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void alterar(Livro livro) {
		this.livro = livro;
	}

	public void remover(Livro livro) {
		new DAO<Livro>(Livro.class).remove(livro);
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
