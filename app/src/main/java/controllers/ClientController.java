package controllers;

import DAO.ClientDAO;
import entities.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClientController {

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public @ResponseBody List<Client> getClients() {
        ClientDAO clientDAO = new ClientDAO();
        return clientDAO.getAll(Client.class);
    }

    @RequestMapping(value = "/add_client", method = RequestMethod.GET)
    public String addClient(@RequestParam(name = "fcn", required = true) String fcn,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "address") String address,
                            @RequestParam(name = "\"e-mail\"") String email,
                            ModelAndView modelAndView) {

        try {
            ClientDAO clientDAO = new ClientDAO();
            Long id = clientDAO.getAll(Client.class).stream()
                    .map(Client::getId)
                    .max(Long::compareTo).orElse(1L);
            Client client = new Client(id, fcn, type, address, email);
//            Client client = new Client(fcn, type, address, email);
            clientDAO.create(client);
            modelAndView.addObject("client", client);
            return "client";
        } catch (Exception e) {
            return "Can't create client.\n";
        }

    }

}
