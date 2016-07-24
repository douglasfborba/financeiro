package br.com.financeiro.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

	private HibernateUtil() { }

	private static SessionFactory buildSessionFactory() {
		try {
			AnnotationConfiguration config = new AnnotationConfiguration();
			config.configure("hibernate.cfg.xml");
			return config.buildSessionFactory();
		} catch (Exception e) {
			System.out.println("Criação inicial do objeto SessionFactory falhou. Erro: " + e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
}
