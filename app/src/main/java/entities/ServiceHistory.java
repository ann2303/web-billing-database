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

    @JoinTable(name = "service")
    @JoinColumn(name = "id")
    @Column(name = "service_id")
    private Long serviceId;

    @JoinTable(name = "number")
    @JoinColumn(name = "number_id")
    @Column(name = "number")
    private Long number;

    @Column(name = "connection_time")
    private Time startTime;

    @Column(name = "disconnection_time")
    private Time endTime;

    @Column(name = "payment_plan")
    private Period paymentPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
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
