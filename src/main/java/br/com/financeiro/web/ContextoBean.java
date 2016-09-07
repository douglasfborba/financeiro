package br.com.financeiro.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import br.com.financeiro.conta.Conta;
import br.com.financeiro.conta.ContaRN;
import br.com.financeiro.usuario.Usuario;
import br.com.financeiro.usuario.UsuarioRN;

@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean implements Serializable {
	private static final long serialVersionUID = -2675240132433506995L;
	private Usuario usuarioLogado = null;
	private Conta contaAtiva = null;
	private Locale localizacao = null;
	private List<Locale> idiomas;

	public Usuario getUsuarioLogado() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		if (this.usuarioLogado == null || !login.equals(this.usuarioLogado.getLogin())) {
			if (login != null) {
				UsuarioRN usuarioRN = new UsuarioRN();
				usuarioLogado = usuarioRN.buscarPorLogin(login);
				this.contaAtiva = null;
			}
		}
		return usuarioLogado;
	}

	public Locale getLocaleUsuario() {
		if (this.localizacao == null) {
			Usuario usuario = this.getUsuarioLogado();
			String idioma = usuario.getIdioma();
			String[] info = idioma.split("_");
			this.localizacao = new Locale(info[0], info[1]);
		}
		return this.localizacao;
	}

	public List<Locale> getIdiomas() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<Locale> locales = context.getApplication().getSupportedLocales();
		this.idiomas = new ArrayList<Locale>();
		while (locales.hasNext()) {
			this.idiomas.add(locales.next());
		}
		return this.idiomas;
	}

	public void setIdiomaUsuario(String idioma) {
		UsuarioRN usuarioRN = new UsuarioRN();
		this.usuarioLogado = usuarioRN.carregar(this.getUsuarioLogado().getCodigo());
		this.getUsuarioLogado().setIdioma(idioma);
		usuarioRN.salvar(this.usuarioLogado);
		String[] info = idioma.split("_");
		Locale locale = new Locale(info[0], info[1]);
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Conta getContaAtiva() {
		if (this.contaAtiva == null) {
			Usuario usuario = this.getUsuarioLogado();
			ContaRN contaRN = new ContaRN();
			this.contaAtiva = contaRN.buscarFavorita(usuario);
			if (this.contaAtiva == null) {
				List<Conta> contas = contaRN.listar(usuario);
				if (contas != null) {
					for (Conta conta : contas) {
						this.contaAtiva = conta;
						break;
					}
				}
			}
		}
		return contaAtiva;
	}

	public void setContaAtiva(ValueChangeEvent event) {
		Integer conta = (Integer) event.getNewValue();
		ContaRN contaRN = new ContaRN();
		this.contaAtiva = contaRN.carregar(conta);
	}
}
