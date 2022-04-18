package DAO;

import entities.Service;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.Assert.*;

class ServiceDAOTest {

    ServiceDAO serviceDAO = new ServiceDAO();

    @Test
    void createTest() {
        String structure = "Интернет : 500";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("select max(id) from service");

        @SuppressWarnings("unchecked")
        Long result = Long.valueOf((Integer) query.list().get(0));
        session.close();
        Service entity = new Service(result + 1, "Интернет500",
                500, 5, 0, structure);
        serviceDAO.create(entity);
        Service service = serviceDAO.getEntityById(result + 1, Service.class);
        notNull(service);
    }

    @Test
    void updateTest() {
        String structure = "Интернет : 700";
        Service entity = new Service(1L, "Интернет700",
                600, 5, 0, structure);
        serviceDAO.update(entity);
        Service service = serviceDAO.getEntityById(1L, Service.class);
        assertEquals(service, entity);
    }

}