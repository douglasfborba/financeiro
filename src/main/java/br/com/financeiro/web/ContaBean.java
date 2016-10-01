package br.com.financeiro.web;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.StreamedContent;

import br.com.financeiro.conta.Conta;
import br.com.financeiro.conta.ContaRN;
import br.com.financeiro.util.UtilException;
import br.com.financeiro.web.util.ContextoUtil;
import br.com.financeiro.web.util.RelatorioUtil;

@ManagedBean(name = "contaBean")
@RequestScoped
public class ContaBean {
	private Conta selecionada = new Conta();
	private List<Conta> lista = null;
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;

	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		this.selecionada.setUsuario(contextoBean.getUsuarioLogado());
		ContaRN contaRN = new ContaRN();
		contaRN.salvar(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void excluir() {
		ContaRN contaRN = new ContaRN();
		contaRN.excluir(this.selecionada);
		this.selecionada = new Conta();
		this.lista = null;
	}

	public void tornarFavorita() {
		ContaRN contaRN = new ContaRN();
		contaRN.tornarFavorita(this.selecionada);
		this.selecionada = new Conta();
	}

	public Conta getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Conta selecionada) {
		this.selecionada = selecionada;
	}

	public List<Conta> getLista() {
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			ContaRN contaRN = new ContaRN();
			this.lista = contaRN.listar(contextoBean.getUsuarioLogado());
		}
		return lista;
	}

	public void setLista(List<Conta> lista) {
		this.lista = lista;
	}

	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		String usuario = contextoBean.getUsuarioLogado().getLogin();
		String nomeRelatorioJasper = "contas";
		String nomeRelatorioSaida = usuario + "_contas";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap<String, Integer> parametrosRelatorio = new HashMap<String, Integer>();
		parametrosRelatorio.put("codigoUsuario", contextoBean.getUsuarioLogado().getCodigo());
		try {
			this.arquivoRetorno = relatorioUtil.gerarRelatorio(parametrosRelatorio, nomeRelatorioJasper,
					nomeRelatorioSaida, this.tipoRelatorio);
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
		return this.arquivoRetorno;
	}

	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}

	public int getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
}
