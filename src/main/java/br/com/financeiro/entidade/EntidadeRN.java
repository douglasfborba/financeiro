package br.com.financeiro.entidade;

import java.util.List;

import br.com.financeiro.usuario.Usuario;
import br.com.financeiro.util.DAOFactory;

public class EntidadeRN {
	private EntidadeDAO entidadeDAO;

	public EntidadeRN() {
		this.entidadeDAO = DAOFactory.criarEntidadeDAO();
	}

	public Entidade carregar(int codigo) {
		return entidadeDAO.carregar(codigo);
	}

	public void salvar(Entidade entidade) {
		this.entidadeDAO.salvar(entidade);
	}

	public void excluir(Entidade entidade) {
		this.entidadeDAO.excluir(entidade);
	}

	public List<Entidade> listar(Usuario usuario) {
		return this.entidadeDAO.listar(usuario);
	}
}
