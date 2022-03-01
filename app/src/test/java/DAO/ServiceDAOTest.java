package DAO;

import entities.Service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.Assert.*;

class ServiceDAOTest {

    ServiceDAO serviceDAO = new ServiceDAO();

    @Test
    void createTest() {
        String structure = "Интернет : 500";
        Service entity = new Service(89L, "Интернет500",
                500, 5, 0, structure);
        serviceDAO.create(entity);
        Service service = serviceDAO.getEntityById(89L, Service.class);
        notNull(service);
    }

    @Test
    void updateTest() {
        String structure = "Интернет : 700";
        Service entity = new Service(89L, "Интернет700",
                600, 5, 0, structure);
        serviceDAO.update(entity);
        Service service = serviceDAO.getEntityById(89L, Service.class);
        assertEquals(service, entity);
    }

}