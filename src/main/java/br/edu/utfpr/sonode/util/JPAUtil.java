package br.edu.utfpr.sonode.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class JPAUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Procura o hibernate.cfg.xml no classpath (src/main/resources)
            sessionFactory = new Configuration()
                .configure() // sem parâmetros, já lê hibernate.cfg.xml
                .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erro ao criar SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
