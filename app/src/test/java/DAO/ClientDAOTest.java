package DAO;

import com.google.common.collect.Lists;
import entities.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;


class ClientDAOTest {

    private ClientDAO clientDAO = new ClientDAO();

    @Test
    void getClientByIdTest() {
        Long id = Long.valueOf(5);
        Client client = clientDAO.getEntityById(id, Client.class);
        Assert.assertEquals(client.getName(), "Киселева Анастасия Никитична");
    }

    @Test
    void filterTest() {
        List<Client> result = clientDAO.filter("nameFilter",
                Lists.newArrayList("%Анастасия%"), Client.class);
        result.forEach(client -> Assert.assertTrue(client.getName().contains("Анастасия")));
    }

    @Test
    void sortTest() {
        List<Client> result = clientDAO.sort(Map.of("name", "asc"), Client.class);
        List<Client> check = Lists.newArrayList(result);
        check.sort(Comparator.comparing(Client::getName));
        Assert.assertEquals(result, check);
    }
}