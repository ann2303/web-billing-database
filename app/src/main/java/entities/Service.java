package entities;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Period;

@FilterDef(name = "nameFilter", parameters = @ParamDef(name = "nameParam", type = "java.lang.String"))
@Filter(name = "nameFilter", condition = "name like :nameParam")
@FilterDef(name = "typeFilter", parameters = @ParamDef(name = "typeParam", type = "java.lang.String"))
@Filter(name = "typeFilter", condition = "structure->:typeParam IS NOT NULL")

@Entity
@Table(name = "service")
@TypeDef(name = "jsonb", typeClass = JsonNode.class)
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "subscription_fee_month")
    private double payPerMounth;

    @Column(name = "subscription_fee_day")
    private double payPerDay;

    @Column(name = "validity")
    private Period duration;

    @Column(name = "connection_cost")
    private double startCost;

    @Type(type = "jsonb")
    @Transient
    @Column(name = "structure")
    private JsonNode structure; // возможно придется писать класс

    public Service(Long id, String name, double payPerMounth, double payPerDay,
                   Period duration, double startCost, JsonNode structure) {
        this.id = id;
        this.name = name;
        this.payPerMounth = payPerMounth;
        this.payPerDay = payPerDay;
        this.duration = duration;
        this.startCost = startCost;
        this.structure = structure;
    }

    public Service() {}

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

