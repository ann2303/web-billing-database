package DAO;

import com.google.common.collect.Sets;
import entities.Client;
import entities.Service;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceDAO implements DAO<Service, Long> {

    public Service getServiceById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Service service = session.get(Service.class, id);
        session.close();
        return service;
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
            Query query = session.createQuery("from Client");

            @SuppressWarnings("unchecked")
            List<Client> clients = (List<Client>) query.list();
            session.close();
            return clients;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Client> sort(Map<String, String> order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Service.class, "SERVICE");
        order.forEach((key, value) -> {
            if (!serviceFields.contains(key)) {
                System.out.println("This is not Service's field! " + key);
            }
            if (value.equals("asc")) {
                criteria.addOrder(Order.asc(key));
            } else if (value.equals("desc")) {
                criteria.addOrder(Order.desc(key));
            }
        });
        return criteria.list();
    }

    public Set<String> serviceFields = Sets.newHashSet(
            "name",
            "subscription_fee_month"
    );

}
