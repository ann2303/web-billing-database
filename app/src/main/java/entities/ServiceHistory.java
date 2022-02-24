package entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.Period;

@Entity
@Table(name = "История услуг")
public class ServiceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(targetEntity = Service.class)
    @JoinColumn(name = "id")
    @Column(name = "id услуги")
    private long serviceId;

    @ManyToOne(targetEntity = Number.class)
    @JoinColumn(name = "номер")
    @Column(name = "номер")
    private long number;

    @Column(name = "время подключения")
    private Time startTime;

    @Column(name = "время отключения")
    private Time endTime;

    @Column(name = "план оплаты")
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
