package br.com.financeiro.web;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.financeiro.categoria.Categoria;
import br.com.financeiro.cheque.Cheque;
import br.com.financeiro.cheque.ChequeID;
import br.com.financeiro.cheque.ChequeRN;
import br.com.financeiro.conta.Conta;
import br.com.financeiro.lancamento.Lancamento;
import br.com.financeiro.lancamento.LancamentoRN;
import br.com.financeiro.util.RNException;
import br.com.financeiro.web.util.ContextoUtil;

@ManagedBean(name = "lancamentoBean")
@ViewScoped
public class LancamentoBean implements Serializable {
	private static final long serialVersionUID = 6719556085307527678L;
	private List<Lancamento> lista;
	private List<Double> saldos = new ArrayList<Double>();
	private float saldoGeral;
	private Lancamento editado = new Lancamento();
	private List<Lancamento> listaAteHoje;
	private List<Lancamento> listaFuturos;
	private Integer numeroCheque;

	public LancamentoBean() {
		this.novo();
	}

	public void novo() {
		this.editado = new Lancamento();
		this.editado.setData(new Date(System.currentTimeMillis()));
		this.numeroCheque = null;
	}

	public void editar() {
		Cheque cheque = this.editado.getCheque();
		if (cheque != null) {
			this.numeroCheque = cheque.getChequeId().getCheque();
		}
	}

	public void salvar() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		this.editado.setUsuario(contextoBean.getUsuarioLogado());
		this.editado.setConta(contextoBean.getContaAtiva());
		ChequeRN chequeRN = new ChequeRN();
		ChequeID chequeId = null;
		if (this.numeroCheque != null) {
			chequeId = new ChequeID();
			chequeId.setConta(contextoBean.getContaAtiva().getConta());
			chequeId.setCheque(this.numeroCheque);
			Cheque cheque = chequeRN.carregar(chequeId);
			FacesContext context = FacesContext.getCurrentInstance();
			if (cheque == null) {
				FacesMessage msg = new FacesMessage("Cheque não cadastrado");
				context.addMessage(null, msg);
				return;
			} else if (cheque.getSituacao() == Cheque.SITUACAO_CHEQUE_CANCELADO) {
				FacesMessage msg = new FacesMessage("Cheque já cancelado");
				context.addMessage(null, msg);
				return;
			} else {
				this.editado.setCheque(cheque);
				chequeRN.baixarCheque(chequeId, this.editado);
			}
		}
		LancamentoRN lancamentoRN = new LancamentoRN();
		lancamentoRN.salvar(this.editado);
		this.novo();
		this.lista = null;
	}

	public void mudouCheque(ValueChangeEvent event) {
		Integer chequeAnterior = (Integer) event.getOldValue();
		if (chequeAnterior != null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			ChequeRN chequeRN = new ChequeRN();
			try {
				chequeRN.desvinculaLancamento(contextoBean.getContaAtiva(), chequeAnterior);
			} catch (RNException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage msg = new FacesMessage(e.getMessage());
				context.addMessage(null, msg);
				return;
			}
		}
	}

	public void excluir() {
		LancamentoRN lancamentoRN = new LancamentoRN();
		this.editado = lancamentoRN.carregar(this.editado.getLancamento());
		lancamentoRN.excluir(this.editado);
		this.lista = null;
	}

	public List<Double> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Double> saldos) {
		this.saldos = saldos;
	}

	public float getSaldoGeral() {
		return saldoGeral;
	}

	public void setSaldoGeral(float saldoGeral) {
		this.saldoGeral = saldoGeral;
	}

	public Lancamento getEditado() {
		return editado;
	}

	public void setEditado(Lancamento editado) {
		this.editado = editado;
	}

	public String getDataCadastroConta() {
		ContextoBean contextoBean = ContextoUtil.getContextoBean();
		Date dataCadastroConta = contextoBean.getContaAtiva().getDataCadastro();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(dataCadastroConta);
	}

	public List<Lancamento> getLista() {
		if (this.lista == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			Calendar dataSaldo = new GregorianCalendar();
			dataSaldo.add(Calendar.MONTH, -1);
			dataSaldo.add(Calendar.DAY_OF_MONTH, -1);
			Calendar inicio = new GregorianCalendar();
			inicio.add(Calendar.MONTH, -1);
			LancamentoRN lancamentoRN = new LancamentoRN();
			try {
				this.saldoGeral = lancamentoRN.saldo(conta, dataSaldo.getTime());
			} catch (RNException e) {
				FacesContext context = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(e.getMessage());
				context.addMessage(null, facesMessage);
			}
			this.lista = lancamentoRN.listar(conta, inicio.getTime(), null);
			Categoria categoria = null;
			double saldo = this.saldoGeral;
			for (Lancamento lancamento : this.lista) {
				categoria = lancamento.getCategoria();
				saldo = saldo + (lancamento.getValor().floatValue() * categoria.getFator());
				this.saldos.add(saldo);
			}
		}
		return this.lista;
	}

	public List<Lancamento> getListaAteHoje() {
		if (this.listaAteHoje == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			Calendar hoje = new GregorianCalendar();
			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaAteHoje = lancamentoRN.listar(conta, null, hoje.getTime());
		}
		return this.listaAteHoje;
	}

	public List<Lancamento> getListaFuturos() {
		if (this.listaFuturos == null) {
			ContextoBean contextoBean = ContextoUtil.getContextoBean();
			Conta conta = contextoBean.getContaAtiva();
			Calendar amanha = new GregorianCalendar();
			amanha.add(Calendar.DAY_OF_MONTH, 1);
			LancamentoRN lancamentoRN = new LancamentoRN();
			this.listaFuturos = lancamentoRN.listar(conta, amanha.getTime(), null);
		}
		return this.listaFuturos;
	}

	public Integer getNumeroCheque() {
		return numeroCheque;
	}

	public void setNumeroCheque(Integer numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
}
