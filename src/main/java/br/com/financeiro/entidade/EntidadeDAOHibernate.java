package br.com.financeiro.entidade;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.usuario.Usuario;

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
	public List<Entidade> listar(Usuario usuario) {
		Criteria criteria = this.session.createCriteria(Entidade.class);
		criteria.add(Restrictions.eq("usuario", usuario));
		return criteria.list();
	}
}
