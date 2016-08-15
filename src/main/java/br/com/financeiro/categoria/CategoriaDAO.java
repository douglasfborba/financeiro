package br.com.financeiro.categoria;

import java.util.List;

import br.com.financeiro.usuario.Usuario;

public interface CategoriaDAO {
	public Categoria salvar(Categoria categoria);

	public void excluir(Categoria categoria);

	public Categoria carregar(int categoria);

	public List<Categoria> listar(Usuario usuario);
}
