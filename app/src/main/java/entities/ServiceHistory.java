package entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.Period;

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
    @JoinColumn(name = "number_id", referencedColumnName = "number_id")
    private Number number;

    @Column(name = "connection_time")
    private Time startTime;

    @Column(name = "disconnection_time")
    private Time endTime;

}
