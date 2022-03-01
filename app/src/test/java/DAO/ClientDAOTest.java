package DAO;

import com.google.common.collect.Lists;
import entities.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


class ClientDAOTest {

    private ClientDAO clientDAO = new ClientDAO();

    @Test
    void getClientByIdTest() {
        Long id = Long.valueOf(5);
        Client client = clientDAO.getClientById(id);
        Assert.assertEquals(client.getName(), "Киселева Анастасия Никитична");
    }

    @Test
    void filterTest() {
        List<Client> result = clientDAO.filter("nameFilter",
                Lists.newArrayList("%Анастасия%"));
        result.forEach(client -> Assert.assertTrue(client.getName().contains("Анастасия")));
    }

    @Test
    void sortTest() {
        List<Client> result = clientDAO.sort(Map.of("name", "asc"));
        Assert.assertTrue(result.get(0).getName().equals("Агеева Анастасия Ивановна"));
    }
}