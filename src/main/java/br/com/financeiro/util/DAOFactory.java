package br.com.financeiro.util;

import br.com.financeiro.categoria.CategoriaDAO;
import br.com.financeiro.categoria.CategoriaDAOHibernate;
import br.com.financeiro.conta.ContaDAO;
import br.com.financeiro.conta.ContaDAOHibernate;
import br.com.financeiro.entidade.EntidadeDAO;
import br.com.financeiro.entidade.EntidadeDAOHibernate;
import br.com.financeiro.lancamento.LancamentoDAO;
import br.com.financeiro.lancamento.LancamentoDAOHibernate;
import br.com.financeiro.usuario.UsuarioDAO;
import br.com.financeiro.usuario.UsuarioDAOHibernate;

public class DAOFactory {
	public static UsuarioDAO criarUsuarioDAO() {
		UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}

	public static ContaDAO criarContaDAO() {
		ContaDAOHibernate contaDAO = new ContaDAOHibernate();
		contaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return contaDAO;
	}

	public static CategoriaDAO criarCategoriaDAO() {
		CategoriaDAOHibernate categoriaDAO = new CategoriaDAOHibernate();
		categoriaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return categoriaDAO;
	}

	public static EntidadeDAO criarEntidadeDAO() {
		EntidadeDAOHibernate entidadeDAO = new EntidadeDAOHibernate();
		entidadeDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return entidadeDAO;
	}

	public static LancamentoDAO criarLancamentoDAO() {
		LancamentoDAOHibernate lancamentoDAO = new LancamentoDAOHibernate();
		lancamentoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return lancamentoDAO;
	}
}
