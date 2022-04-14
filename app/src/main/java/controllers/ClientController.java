package controllers;

import DAO.ClientDAOImpl;
import entities.Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import services.ClientService;

import java.util.List;

@Controller
public class ClientController {

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public String getClients(Model model) {
        ClientService clientService = new ClientService();
        List<Client> all = clientService.getAll();
        model.addAttribute("clients", all);
        return "client";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/add_client", method = RequestMethod.GET)
    public String addClient(@RequestParam(name = "fcn", required = true) String fcn,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "email") String email,
                            Model model) {

        try {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
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
            ClientDAOImpl clientDAO = new ClientDAOImpl();
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
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        Client client = clientDAO.getEntityById(id, Client.class);
        model.addAttribute("client",
                client);
        return "client_page";
    }

    @RequestMapping(value = "/delete_client", method = RequestMethod.GET)
    public String deleteClient(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
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
