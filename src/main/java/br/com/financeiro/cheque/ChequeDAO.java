package br.com.financeiro.cheque;

import java.util.List;

import br.com.financeiro.conta.Conta;

public interface ChequeDAO {
	public void salvar(Cheque cheque);

	public void excluir(Cheque cheque);

	public Cheque carregar(ChequeID chequeId);

	public List<Cheque> lista(Conta conta);
}
