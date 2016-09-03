package br.com.financeiro.entidade;

import java.util.List;

public interface EntidadeDAO {
	public void salvar(Entidade entidade);

	public void excluir(Entidade entidade);

	public Entidade carregar(int codigo);

	public List<Entidade> listar();
}
