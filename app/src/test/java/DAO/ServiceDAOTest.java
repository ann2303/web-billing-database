package DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Service;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import util.HibernateUtil;

import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class ServiceDAOTest {

    ServiceDAO serviceDAO = new ServiceDAO();

    @Test
    void createTest() throws JsonProcessingException {
        String json = "{ \"Интернет\" : \"500\" } ";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        Service entity = new Service(Long.valueOf(89), "Интернет500",
                500, 5, Period.of(0, 1, 0), 0, jsonNode);
        serviceDAO.create(entity);
        Service service = serviceDAO.getServiceById(Long.valueOf(89));
        Assert.notNull(service);
    }

}