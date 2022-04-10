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

    @RequestMapping(value = "/add_client", method = RequestMethod.GET)
    public String addClient(@RequestParam(name = "fcn", required = true) String fcn,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "\"e-mail\"") String email,
                            ModelAndView modelAndView) {

        try {
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            Long id = clientDAO.getAll(Client.class).stream()
                    .map(Client::getId)
                    .max(Long::compareTo).orElse(1L);
            Client client = new Client(id, fcn, type, address, email);
//            Client client = new Client(fcn, type, address, email);
            clientDAO.create(client);
            modelAndView.addObject("client", client);
            return "client.html";
        } catch (Exception e) {
            return "Can't create client.\n";
        }

    }

}
