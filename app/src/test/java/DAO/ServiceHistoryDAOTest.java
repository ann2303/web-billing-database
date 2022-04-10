package DAO;

import entities.ServiceHistory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class ServiceHistoryDAOTest {

    ServiceHistoryDAOImpl serviceHistoryDAOImpl = new ServiceHistoryDAOImpl();

    @Test
    public void getAllTest() {
        List<ServiceHistory> all = serviceHistoryDAOImpl.getAll(ServiceHistory.class);
        Assert.assertNotNull(all);
    }

}