package br.com.financeiro.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;

import br.com.financeiro.util.HibernateUtil;

public class ConexaoHibernateFilter implements Filter {
	private SessionFactory sessionFactory;

	public void init(FilterConfig fConfig) throws ServletException {
		this.sessionFactory = HibernateUtil.getSessionFactory();
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			this.sessionFactory.getCurrentSession().beginTransaction();
			chain.doFilter(request, response);
			this.sessionFactory.getCurrentSession().getTransaction().commit();
			this.sessionFactory.getCurrentSession().close();
		} catch (Throwable e) {
			try {
				if (this.sessionFactory.getCurrentSession().getTransaction().isActive()) {
					this.sessionFactory.getCurrentSession().getTransaction().rollback();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			throw new ServletException(e);
		}
	}

	public void destroy() {	}
}
