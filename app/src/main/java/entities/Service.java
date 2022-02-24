package entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Period;
import java.util.Set;

@Entity
@Table(name = "Услуга")
@TypeDef(name = "jsonb", typeClass = JsonNode.class)
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "Наименование")
    private String name;

    @Column(name = "Абонентская плата (в месяц)")
    private Double payPerMounth;

    @Column(name = "Абонентская плата (в день)")
    private Double payPerDay;

    @Column(name = "Срок действия")
    private Period duration;

    @Column(name = "Стоимость подключения")
    private Double startCost;

    @Type(type = "jsonb")
    @Column(name = "Состав")
    private JsonNode structure; // возможно придется писать класс

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPayPerMounth() {
        return payPerMounth;
    }

    public void setPayPerMounth(Double payPerMounth) {
        this.payPerMounth = payPerMounth;
    }

    public Double getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(Double payPerDay) {
        this.payPerDay = payPerDay;
    }

    public Period getDuration() {
        return duration;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public Double getStartCost() {
        return startCost;
    }

    public void setStartCost(Double startCost) {
        this.startCost = startCost;
    }

    public JsonNode getStructure() {
        return structure;
    }

    public void setStructure(JsonNode structure) {
        this.structure = structure;
    }

    public Set<ServiceHistory> getHistories() {

    }
}

