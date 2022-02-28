package DAO;

import com.google.common.collect.Lists;
import entities.Client;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.List;


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
        Assert.assertEquals(2, result.size());
    }
}