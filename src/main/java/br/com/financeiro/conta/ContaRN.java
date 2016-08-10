package br.com.financeiro.conta;

import java.util.Date;
import java.util.List;

import br.com.financeiro.usuario.Usuario;
import br.com.financeiro.util.DAOFactory;

public class ContaRN {
	private ContaDAO contaDAO;

	public ContaRN() {
		this.contaDAO = DAOFactory.criarContaDAO();
	}

	public Conta carregar(int codigo) {
		return contaDAO.carregar(codigo);
	}

	public void salvar(Conta conta) {
		conta.setDataCadastro(new Date());
		this.contaDAO.salvar(conta);
	}

	public void excluir(Conta conta) {
		this.contaDAO.excluir(conta);
	}

	public List<Conta> listar(Usuario usuario) {
		return this.contaDAO.listar(usuario);
	}

	public Conta buscarFavorita(Usuario usuario) {
		return this.contaDAO.buscarFavorita(usuario);
	}

	public void tornarFavorita(Conta contaFavorita) {
		Conta conta = this.buscarFavorita(contaFavorita.getUsuario());
		if (conta != null) {
			conta.setFavorita(false);
			this.contaDAO.salvar(conta);
		}
		contaFavorita.setFavorita(true);
		this.contaDAO.salvar(contaFavorita);
	}
}
