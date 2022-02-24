package entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Period;

@Entity
@Table(name = "Услуга")
@TypeDef(name = "jsonb", typeClass = JsonNode.class)
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "Наименование")
    private String name;

    @Column(name = "Абонентская плата (в месяц)")
    private double payPerMounth;

    @Column(name = "Абонентская плата (в день)")
    private double payPerDay;

    @Column(name = "Срок действия")
    private Period duration;

    @Column(name = "Стоимость подключения")
    private double startCost;

    @Type(type = "jsonb")
    @Column(name = "Состав")
    private JsonNode structure; // возможно придется писать класс

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPayPerMounth() {
        return payPerMounth;
    }

    public void setPayPerMounth(double payPerMounth) {
        this.payPerMounth = payPerMounth;
    }

    public double getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(double payPerDay) {
        this.payPerDay = payPerDay;
    }

    public Period getDuration() {
        return duration;
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public double getStartCost() {
        return startCost;
    }

    public void setStartCost(double startCost) {
        this.startCost = startCost;
    }

    public JsonNode getStructure() {
        return structure;
    }

    public void setStructure(JsonNode structure) {
        this.structure = structure;
    }

}

