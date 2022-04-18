package entities;

import DAO.NumberDAO;
import DAO.ServiceDAO;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "service_history")
public class ServiceHistory {
    public Long getId() {
        return id;
    }

    public Service getService() {
        return service;
    }

    public Number getNumber() {
        return number;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

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
        ServiceDAO serviceDAO = new ServiceDAO();
        this.service = serviceDAO.getEntityById(service_id, Service.class);
        NumberDAO numberDAO = new NumberDAO();
        this.number = numberDAO.getEntityById(number_id, Number.class);
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public ServiceHistory() {}

}
