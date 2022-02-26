package DAO;

import entities.Number;
import org.hibernate.Session;
import util.HibernateUtil;

public class NumberDAO implements DAO<Number, Long> {
    
    public Number getNumberById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Number Number = session.get(Number.class, id);
        session.close();
        return Number;
    }
    
}
