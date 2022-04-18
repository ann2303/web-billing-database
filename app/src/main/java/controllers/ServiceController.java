package controllers;

import DAO.ServiceDAO;
import DAO.ServiceDAO;
import entities.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ServiceController {
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String getservices(Model model) {
        ServiceDAO serviceDAO = new ServiceDAO();
        List<Service> all = serviceDAO.getAll(Service.class);
        model.addAttribute("services", all);
        return "service/service";
    }

    @RequestMapping(value = "/service/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "service/registration";
    }

    @RequestMapping(value = "/add_service", method = RequestMethod.GET)
    public String addService(@RequestParam(name = "name", required = true) String name,
                             @RequestParam(name = "pay_per_mounth") double payPerMounth,
                             @RequestParam(name = "pay_per_day") double payPerDay,
                             @RequestParam(name = "start_cost") double startCost,
                             @RequestParam(name = "structure") String structure,
                             Model model) {

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            long id = serviceDAO.getAll(Service.class).stream()
                    .map(Service::getId).max(Long::compareTo).orElse(1L);
            Service service = new Service(id, name, payPerMounth, payPerDay, startCost, structure);
            serviceDAO.create(service);
            String res = String.format("service added successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't create service.");
            return "error";
        }

    }

    @RequestMapping(value = "/update_service", method = RequestMethod.GET)
    public String updateService(@RequestParam(name = "id", required = true) Long id,
                                @RequestParam(name = "name", required = true) String name,
                                @RequestParam(name = "pay_per_mounth") double payPerMounth,
                                @RequestParam(name = "pay_per_day") double payPerDay,
                                @RequestParam(name = "start_cost") double startCost,
                                @RequestParam(name = "structure") String structure,
                                Model model) {

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            Service service = new Service(id, name, payPerMounth, payPerDay, startCost, structure);
            serviceDAO.update(service);
            String res = String.format("service updated successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't update service.");
            return "error";
        }

    }

    @RequestMapping(value = "/service_page", method = RequestMethod.GET)
    public String pageService(@RequestParam(name = "id", required = true) Long id,
                             Model model) {
        ServiceDAO serviceDAO = new ServiceDAO();
        Service service = serviceDAO.getEntityById(id, Service.class);
        model.addAttribute("service",
                service);
        return "service/service_page";
    }

    @RequestMapping(value = "/delete_service", method = RequestMethod.GET)
    public String deleteservice(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            ServiceDAO serviceDAO = new ServiceDAO();
            Service service = serviceDAO.getEntityById(id, Service.class);
            serviceDAO.delete(service);
            String res = String.format("service deleted successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't delete service.");
            return "error";
        }

    }
}
