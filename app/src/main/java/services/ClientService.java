package services;

import DAO.ClientDAO;
import DAO.ClientDAOImpl;
import entities.Client;

import java.util.List;

public class ClientService {
    private ClientDAO ClientDAO = new ClientDAOImpl();
    public void createClient(Client client) {
        ClientDAO.create(client);
    }

    public void deleteClient(Client client) {
        ClientDAO.delete(client);
    }

    public void updateClient(Client client) {
        ClientDAO.update(client);
    }

    public List<Client> getAll() {
        return ClientDAO.getAll(Client.class);
    }

}
