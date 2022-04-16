package controllers;

import DAO.NumberDAO;
import DAO.NumberDAOImpl;
import entities.Number;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NumberController {

    @RequestMapping(value = "/numbers", method = RequestMethod.GET)
    public String getNumbers(Model model) {
        NumberDAO numberService = new NumberDAOImpl();
        List<Number> all = numberService.getAll(Number.class);
        model.addAttribute("numbers", all);
        return "number/number";
    }

    @RequestMapping(value = "/number/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "number/registration";
    }

    @RequestMapping(value = "/add_number", method = RequestMethod.GET)
    public String addNumber(@RequestParam(name = "number", required = true) Long number_id,
                            @RequestParam(name = "client_id") Long client_id,
                            @RequestParam(name = "balance") double balance,
                            @RequestParam(name = "max_credit") double max_credit,
                            Model model) {

        try {
            NumberDAOImpl numberDAO = new NumberDAOImpl();
            Number number = new Number(number_id, client_id, balance, max_credit);
            numberDAO.create(number);
            String res = String.format("Number added successfully with id = %d", number_id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't create number.");
            return "error";
        }

    }

    @RequestMapping(value = "/update_number", method = RequestMethod.GET)
    public String updateNumber(@RequestParam(name = "number_id", required = true) Long number_id,
                               @RequestParam(name = "client_id") Long client_id,
                               @RequestParam(name = "balance") double balance,
                               @RequestParam(name = "max_credit") double max_credit,
                               Model model) {

        try {
            NumberDAOImpl numberDAO = new NumberDAOImpl();
            Number number = new Number(number_id, client_id, balance, max_credit);
            numberDAO.update(number);
            String res = String.format("Number updated successfully with id = %d", number_id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't update number.");
            return "error";
        }

    }


    @RequestMapping(value = "/delete_number", method = RequestMethod.GET)
    public String deleteNumber(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            NumberDAOImpl numberDAO = new NumberDAOImpl();
            Number number = numberDAO.getEntityById(id, Number.class);
            numberDAO.delete(number);
            String res = String.format("Number deleted successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't delete number.");
            return "error";
        }

    }


}
