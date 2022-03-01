package util;

import entities.Client;
import entities.Service;
import entities.ServiceHistory;
import entities.Transactions;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Service.class)
                    .addAnnotatedClass(Number.class)
                    .addAnnotatedClass(ServiceHistory.class)
                    .addAnnotatedClass(Transactions.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
