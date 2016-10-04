package br.com.financeiro.entidade;

import java.util.List;

import br.com.financeiro.usuario.Usuario;

public interface EntidadeDAO {
	public void salvar(Entidade entidade);

	public void excluir(Entidade entidade);

	public Entidade carregar(int codigo);

	public List<Entidade> listar(Usuario usuario);
}
