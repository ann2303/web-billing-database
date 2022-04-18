package controllers;

import DAO.ServiceDAO;
import entities.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RootPageController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String registration(Model model) {
        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> all = serviceDAO.getAll(Service.class);
        model.addAttribute("services", all);
        return "index";
    }
}
