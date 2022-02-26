package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public interface DAO<E, K> {

    default E update(E entity) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("An exception was thrown during the update of "
                    + entity.getClass().getName() + ": " + e.getMessage());
        }
        return entity;
    }

    default boolean delete(E entity) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("An exception was thrown during deletion of "
                    + entity.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    default boolean create(E entity) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println("An exception was thrown during creation of "
                    + entity.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

}
