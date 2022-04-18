package controllers;

import DAO.ServiceHistoryDAO;
import entities.ServiceHistory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class ServiceHistoryController {

    @RequestMapping(value = "/service_history", method = RequestMethod.GET)
    public String getServiceHistory(Model model) {
        ServiceHistoryDAO service_historyDAO = new ServiceHistoryDAO();
        List<ServiceHistory> all = service_historyDAO.getAll(ServiceHistory.class);
        model.addAttribute("service_history", all);
        return "service_history/service_history";
    }

    @RequestMapping(value = "/service_history/registration", method = RequestMethod.GET)
    public String registration(@RequestParam(name = "number", required = true) Long number,
                               @RequestParam(name = "service_id") Long service_id,
                               Model model) {
        model.addAttribute("number", number);
        model.addAttribute("service_id", service_id);
        return "service_history/registration";
    }

    @RequestMapping(value = "/add_service_history", method = RequestMethod.GET)
    public String addServiceHistory(@RequestParam(name = "number", required = true) Long number,
                            @RequestParam(name = "service_id") Long service_id,
                            @RequestParam(name = "start_time") Time start_time,
                            @RequestParam(name = "end_time") Time end_time,
                            Model model) {

        try {
            ServiceHistoryDAO service_historyDAO = new ServiceHistoryDAO();
            long id = service_historyDAO.getAll(ServiceHistory.class).stream()
                    .map(ServiceHistory::getId).max(Long::compareTo).orElse(1L);
            ServiceHistory service_history = new ServiceHistory(id, number, service_id, start_time, end_time);
            service_historyDAO.create(service_history);
            String res = String.format("ServiceHistory added successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't create service_history.");
            return "error";
        }

    }

    @RequestMapping(value = "/delete_service_history", method = RequestMethod.GET)
    public String deleteServiceHistory(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            ServiceHistoryDAO service_historyDAO = new ServiceHistoryDAO();
            ServiceHistory service_history = service_historyDAO.getEntityById(id, ServiceHistory.class);
            service_historyDAO.delete(service_history);
            String res = String.format("ServiceHistory deleted successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't delete service_history.");
            return "error";
        }

    }

}
