package br.com.financeiro.entidade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

public class EntidadeDAOHibernate implements EntidadeDAO {
	private Session session;

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public void salvar(Entidade entidade) {
		this.session.saveOrUpdate(entidade);
	}

	@Override
	public void excluir(Entidade entidade) {
		this.session.delete(entidade);
	}

	@Override
	public Entidade carregar(int codigo) {
		return (Entidade) this.session.get(Entidade.class, codigo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Entidade> listar() {
		Criteria criteria = this.session.createCriteria(Entidade.class);
		return criteria.list();
	}
}
