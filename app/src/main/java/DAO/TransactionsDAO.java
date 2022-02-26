package DAO;

import entities.Transactions;
import org.hibernate.Session;
import util.HibernateUtil;

public class TransactionsDAO implements DAO<Transactions, Long> {

    public Transactions getTransactionById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transactions Transactions = session.get(Transactions.class, id);
        session.close();
        return Transactions;
    }
}
