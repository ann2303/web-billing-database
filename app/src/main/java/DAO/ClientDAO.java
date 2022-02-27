package DAO;

import com.google.common.collect.Sets;
import entities.Client;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientDAO implements DAO<Client, Long> {

    public Client getClientById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    public List<Client> filterByField(String field, Filter filter) {

        if (!clientFields.contains(field)) {
            return new ArrayList<>();
        }

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String sql;
            sql = "select id, fcn, type, address, email from client where " + field + " like :log";
            Query query = session.createSQLQuery(sql)
                    .addEntity(Client.class)
                    .setParameter("log", filter);
            List<Client> clients = query.list();
            session.close();
            return clients;
        } catch (Exception e) {
            return null;
        }
    }


    public Set<String> clientFields = Sets.newHashSet(
        "fcn",
        "type",
        "address",
        "email"
    );

}
