package DAO;

import entities.ServiceHistory;
import org.hibernate.Session;
import util.HibernateUtil;

public class ServiceHistoryDAO implements DAO<ServiceHistory, Long> {

    public ServiceHistory getServiceHistoryById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ServiceHistory ServiceHistory = session.get(ServiceHistory.class, id);
        session.close();
        return ServiceHistory;
    }
}
