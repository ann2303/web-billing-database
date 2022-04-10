package DAO;

import com.google.common.collect.Lists;
import entities.Client;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;


class ClientDAOTest {

    private ClientDAOImpl clientDAOImpl = new ClientDAOImpl();

    @Test
    void getClientByIdTest() {
        Long id = Long.valueOf(5);
        Client client = clientDAOImpl.getEntityById(id, Client.class);
        Assert.assertEquals(client.getName(), "Киселева Анастасия Никитична");
    }

    @Test
    void filterTest() {
        List<Client> result = clientDAOImpl.filter(Map.of("nameFilter",
                Lists.newArrayList("%Анастасия%")), Client.class);
        result.forEach(client -> Assert.assertTrue(client.getName().contains("Анастасия")));
    }

    @Test
    void sortTest() {
        List<Client> result = clientDAOImpl.sort(Map.of("name", "asc"), Client.class);
        List<Client> check = Lists.newArrayList(result);
        check.sort(Comparator.comparing(Client::getName));
        Assert.assertEquals(result, check);

        result = clientDAOImpl.sort(Map.of("name", "desc"), Client.class);
        check = Lists.newArrayList(result);
        check.sort(Comparator.comparing(Client::getName).reversed());
        Assert.assertEquals(result, check);
    }
}