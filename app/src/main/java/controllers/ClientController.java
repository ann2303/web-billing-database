package controllers;

import DAO.ClientDAO;
import entities.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String getClients(Model model) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> all = clientDAO.getAll(Client.class);
        model.addAttribute("clients", all);
        return "client/client";
    }

    @RequestMapping(value = "/filter_clients", method = RequestMethod.GET)
    public String filterSortClients(@RequestParam(name = "name_filter") String name_filter,
                                    @RequestParam(name = "sort") String sort,
                                    Model model) {
        ClientDAO clientDAO = new ClientDAO();
        List<Client> result;
        if (Objects.nonNull(name_filter)) {
            result = clientDAO.getAll(Client.class).stream()
                    .filter(client -> client.getName().contains(name_filter))
                    .collect(Collectors.toList());
        } else {
            result = clientDAO.getAll(Client.class);
        }
        if (Objects.nonNull(sort)) {
            if (sort.equals("По возрастанию")) {
                result.sort(Comparator.comparing(Client::getName));
            } else if (sort.equals("По убыванию")) {
                result.sort((Client o1, Client o2) ->
                        o2.getName().compareTo(o1.getName()));
            }
        }
        model.addAttribute("clients", result);
        return "client/client";
    }

    @RequestMapping(value = "/client/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "client/registration";
    }

    @RequestMapping(value = "/add_client", method = RequestMethod.GET)
    public String addClient(@RequestParam(name = "fcn", required = true) String fcn,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "email") String email,
                            Model model) {

        try {
            ClientDAO clientDAO = new ClientDAO();
            long id = clientDAO.getAll(Client.class).stream()
                    .map(Client::getId).max(Long::compareTo).orElse(1L);
            Client client = new Client(id, fcn, type, address, email);
            clientDAO.create(client);
            String res = String.format("Client added successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't create client.");
            return "error";
        }

    }

    @RequestMapping(value = "/update_client", method = RequestMethod.GET)
    public String updateClient(@RequestParam(name = "id", required = true) Long id,
                            @RequestParam(name = "fcn", required = true) String fcn,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "email") String email,
                            Model model) {

        try {
            ClientDAO clientDAO = new ClientDAO();
            Client client = new Client(id, fcn, type, address, email);
            clientDAO.update(client);
            String res = String.format("Client updated successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't update client.");
            return "error";
        }

    }

    @RequestMapping(value = "/client_page", method = RequestMethod.GET)
    public String pageClient(@RequestParam(name = "id", required = true) Long id,
                               Model model) {
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getEntityById(id, Client.class);
        model.addAttribute("client",
                client);
        return "client/client_page";
    }

    @RequestMapping(value = "/delete_client", method = RequestMethod.GET)
    public String deleteClient(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.getEntityById(id, Client.class);
            clientDAO.delete(client);
            String res = String.format("Client deleted successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't delete client.");
            return "error";
        }

    }


}
