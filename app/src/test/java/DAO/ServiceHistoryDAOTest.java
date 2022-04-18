package DAO;

import entities.ServiceHistory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServiceHistoryDAOTest {

    ServiceHistoryDAO serviceHistoryDAO = new ServiceHistoryDAO();

    @Test
    public void getAllTest() {
        List<ServiceHistory> all = serviceHistoryDAO.getAll(ServiceHistory.class);
        Assert.assertNotNull(all);
    }

}