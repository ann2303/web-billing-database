package entities;

import DAO.NumberDAOImpl;
import DAO.ServiceDAOImpl;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "service_history")
public class ServiceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Service service;

    @ManyToOne(targetEntity = Number.class)
    @JoinColumn(name = "number", referencedColumnName = "number_id")
    private Number number;

    @Column(name = "connection_time")
    private Time startTime;

    @Column(name = "disconnection_time")
    private Time endTime;

    public ServiceHistory(Long id, Long service_id, Long number_id, Time startTime, Time endTime) {

        this.id = id;
        ServiceDAOImpl serviceDAO = new ServiceDAOImpl();
        this.service = serviceDAO.getEntityById(service_id, Service.class);
        NumberDAOImpl numberDAO = new NumberDAOImpl();
        this.number = numberDAO.getEntityById(number_id, Number.class);
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public ServiceHistory() {}

}
