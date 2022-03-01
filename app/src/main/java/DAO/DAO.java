package DAO;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public interface DAO<E, K> {

    default E update(E entity) {

        try {
            if (Objects.isNull(entity)) {
                return entity;
            }
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
            if (Objects.isNull(entity)) {
                return false;
            }
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
            if (Objects.isNull(entity)) {
                return false;
            }
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

    default E getEntityById(Long id, Class persistentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        E entity = (E) session.get(persistentClass, id);
        session.close();
        return entity;
    }

    default List<E> filter(String filterName, List parameters, Class persistentClass) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Filter enableFilter = session.enableFilter(filterName);
            Set<String> paramNames = enableFilter.getFilterDefinition().getParameterNames();
            AtomicInteger i = new AtomicInteger();
            paramNames.forEach(name ->
                    enableFilter.setParameter(name, parameters.get(i.getAndIncrement()))
            );
            Query query = session.createQuery("from " + persistentClass.getName());

            @SuppressWarnings("unchecked")
            List<E> result = query.list();
            session.close();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    default List<E> sort(Map<String, String> order, Class persistentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(persistentClass, "CRITERIA");
        order.forEach((key, value) -> {
            if (value.equals("asc")) {
                criteria.addOrder(Order.asc(key));
            } else if (value.equals("desc")) {
                criteria.addOrder(Order.desc(key));
            }
        });
        return criteria.list();
    }


}
