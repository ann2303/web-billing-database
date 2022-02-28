package DAO;

import com.google.common.collect.Sets;
import entities.Client;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientDAO implements DAO<Client, Long> {

    public Client getClientById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    public List<Client> filter(String filterName, List parameters) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Filter enableFilter = session.enableFilter(filterName);
            Set<String> paramNames = enableFilter.getFilterDefinition().getParameterNames();
            AtomicInteger i = new AtomicInteger();
            paramNames.forEach(name ->
                enableFilter.setParameter(name, parameters.get(i.getAndIncrement()))
            );
            Query query = session.createQuery("select id, name, address, type, email from entities.Client");
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
