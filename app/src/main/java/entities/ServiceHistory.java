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
    private long id;

    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(name = "id")
    @Column(name = "service_id")
    private long serviceId;

    @ManyToOne(targetEntity = Number.class)
    @JoinColumn(name = "number")
    @Column(name = "number")
    private long number;

    @Column(name = "connection_time")
    private Time startTime;

    @Column(name = "disconnection_time")
    private Time endTime;

    @Column(name = "payment_plan")
    private Period paymentPlan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Period getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(Period paymentPlan) {
        this.paymentPlan = paymentPlan;
    }
}
