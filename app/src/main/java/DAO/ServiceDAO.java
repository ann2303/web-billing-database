package DAO;

import entities.Service;
import org.hibernate.Session;
import util.HibernateUtil;

public class ServiceDAO implements DAO<Service, Long> {

    public Service getServiceById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Service Service = session.get(Service.class, id);
        session.close();
        return Service;
    }

}
