package br.com.financeiro.lancamento;

import java.util.Date;
import java.util.List;

import br.com.financeiro.conta.Conta;
import br.com.financeiro.util.DAOFactory;
import br.com.financeiro.util.RNException;

public class LancamentoRN {
	private LancamentoDAO lancamentoDAO;

	public LancamentoRN() {
		this.lancamentoDAO = DAOFactory.criarLancamentoDAO();
	}

	public void salvar(Lancamento lancamento) {
		this.lancamentoDAO.salvar(lancamento);
	}

	public void excluir(Lancamento lancamento) {
		this.lancamentoDAO.excluir(lancamento);
	}

	public Lancamento carregar(Integer lancamento) {
		return this.lancamentoDAO.carregar(lancamento);
	}

	public float saldo(Conta conta, Date data) throws RNException {
		if (data.before(conta.getDataCadastro())) {
			throw new RNException("Data solicitada anterior à criação da conta.");
		}
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = this.lancamentoDAO.saldo(conta, data);
		return saldoInicial + saldoNaData;
	}

	public List<Lancamento> listar(Conta conta, Date dataInicio, Date dataFim) {
		return this.lancamentoDAO.listar(conta, dataInicio, dataFim);
	}
}
