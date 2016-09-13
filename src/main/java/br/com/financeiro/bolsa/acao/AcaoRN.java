package br.com.financeiro.bolsa.acao;

import java.io.IOException;
import java.util.List;

import br.com.financeiro.usuario.Usuario;
import br.com.financeiro.util.DAOFactory;
import br.com.financeiro.util.RNException;
import br.com.financeiro.util.YahooFinanceUtil;

public class AcaoRN {
	private AcaoDAO acaoDAO;

	public AcaoRN() {
		this.acaoDAO = DAOFactory.criarAcaoDAO();
	}

	public void salvar(Acao acao) {
		this.acaoDAO.salvar(acao);
	}

	public void excluir(Acao acao) {
		this.acaoDAO.excluir(acao);
	}

	public Acao carregar(String codigo) {
		return this.acaoDAO.carregar(codigo);
	}

	public List<Acao> listar(Usuario usuario) {
		return this.acaoDAO.listar(usuario);
	}

	public List<AcaoVirtual> listarAcaoVirtual(Usuario usuario) throws RNException {
		return null;
	}

	public String retornaCotacao(int indiceInformacao, Acao acao) throws RNException {
		YahooFinanceUtil yahooFinanceUtil = null;
		String informacao = null;
		try {
			yahooFinanceUtil = new YahooFinanceUtil(acao);
			informacao = yahooFinanceUtil.retornaCotacao(indiceInformacao, acao.getSigla());
		} catch (IOException e) {
			throw new RNException("Nâo foi possível recuperar cotação. Erro: " + e.getMessage());
		}
		return informacao;
	}

	public String montaLinkAcao(Acao acao) {
		String link = null;
		if (acao != null) {
			if (acao.getOrigem() != null) {
				if (acao.getOrigem() == YahooFinanceUtil.ORIGEM_BOVESPA) {
					link = acao.getSigla() + YahooFinanceUtil.POSFIXO_ACAO_BOVESPA;
				} else {
					link = acao.getSigla();
				}
			} else {
				link = YahooFinanceUtil.INDICE_BOVESPA;
			}
		} else {
			link = YahooFinanceUtil.INDICE_BOVESPA;
		}
		return link;
	}
}
