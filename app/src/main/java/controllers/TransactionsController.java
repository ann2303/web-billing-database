package controllers;

import DAO.TransactionsDAOImpl;
import entities.Transactions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class TransactionsController {

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public String getTransactions(Model model) {
        TransactionsDAOImpl transactionsDAO = new TransactionsDAOImpl();
        List<Transactions> all = transactionsDAO.getAll(Transactions.class);
        model.addAttribute("transactions", all);
        return "transactions/transactions";
    }

    @RequestMapping(value = "/transactions/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        return "transactions/registration";
    }

    @RequestMapping(value = "/transactions/add_transactions", method = RequestMethod.GET)
    public String addTransactions(@RequestParam(name = "number", required = true) Long number,
                            @RequestParam(name = "type") String type,
                            @RequestParam(name = "time") Timestamp time,
                            @RequestParam(name = "sum") Double sum,
                            Model model) {

        try {
            TransactionsDAOImpl transactionsDAO = new TransactionsDAOImpl();
            long id = transactionsDAO.getAll(Transactions.class).stream()
                    .map(Transactions::getId).max(Long::compareTo).orElse(1L);
            Transactions transactions = new Transactions(id, number, type, sum, time);
            transactionsDAO.create(transactions);
            String res = String.format("Transactions added successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't create transactions.");
            return "error";
        }

    }

    @RequestMapping(value = "/delete_transactions", method = RequestMethod.GET)
    public String deleteTransactions(@RequestParam(name = "id", required = true) Long id,
                               Model model) {

        try {
            TransactionsDAOImpl transactionsDAO = new TransactionsDAOImpl();
            Transactions transactions = transactionsDAO.getEntityById(id, Transactions.class);
            transactionsDAO.delete(transactions);
            String res = String.format("Transactions deleted successfully with id = %d", id);
            model.addAttribute("msg",
                    res);
            return "successful";
        } catch (Exception e) {
            model.addAttribute("error",
                    "Can't delete transactions.");
            return "error";
        }

    }

}
