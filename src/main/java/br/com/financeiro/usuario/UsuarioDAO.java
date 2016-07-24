package br.com.financeiro.usuario;

import java.util.List;

public interface UsuarioDAO {
	public void salvar(Usuario usuario);

	public void atualizar(Usuario usuario);

	public void excluir(Usuario usuario);

	public Usuario carregar(int codigo);

	public Usuario buscarPorLogin(String login);

	public List<Usuario> listar();
}
