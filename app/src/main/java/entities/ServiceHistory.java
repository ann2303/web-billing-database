package entities;

import DAO.NumberDAO;
import DAO.ServiceDAO;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "service_history")
public class ServiceHistory {
    public Long getId() {
        return id;
    }

    public Long getService() {
        return service_id;
    }

    public Long getNumber() {
        return number;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinTable(name = "services")
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private Long service_id;

    @JoinTable(name = "numbers")
    @JoinColumn(name = "number", referencedColumnName = "number_id")
    private Long number;

    @Column(name = "connection_time")
    private Timestamp startTime;

    @Column(name = "disconnection_time")
    private Timestamp endTime;

    public ServiceHistory(Long id, Long service_id, Long number, Timestamp startTime, Timestamp endTime) {

        this.id = id;
        this.service_id = service_id;
        this.number = number;
        this.startTime = startTime;
        this.endTime = endTime;

    }

    public ServiceHistory() {}

}
