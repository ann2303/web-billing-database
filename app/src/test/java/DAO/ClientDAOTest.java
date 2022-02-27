package DAO;

import entities.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ClientDAOTest {

    private ClientDAO clientDAO = new ClientDAO();

    @Test
    void getClientByIdTest() {
        Long id = Long.valueOf(5);
        Client client = clientDAO.getClientById(id);
        Assert.assertEquals(client.getName(), "Киселева Анастасия Никитична");
    }

    @Test
    void filterByField() {

    }
}