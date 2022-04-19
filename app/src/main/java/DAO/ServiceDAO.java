package DAO;

import entities.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class ServiceDAO implements DAO<Service, Long> {
    @Override
    public List<Service> getAll(Class persistentClass) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from " + persistentClass.getName());

        @SuppressWarnings("unchecked")
        List<Service> result = query.list();
        session.close();
        return result;
    }
}
