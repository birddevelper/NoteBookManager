package payconiq.example.stockmanagerrest.entities;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Stock extends RepresentationModel<Stock> {
    private Integer ID;
    private String name;
    private double currentPrice;
    private Timestamp lastUpdate;

    public Stock(){

    }

    public Stock(Integer ID, String name, double currentPrice){
        this.ID = ID;
        this.name = name;
        this.currentPrice = currentPrice;
    }
    public Stock( String name, double currentPrice){
        this.name = name;
        this.currentPrice = currentPrice;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "currentPrice")
    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Basic
    @Column(name = "lastUpdate")
    @UpdateTimestamp

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return ID == stock.ID && currentPrice == stock.currentPrice && Objects.equals(name, stock.name) && Objects.equals(lastUpdate, stock.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, currentPrice, lastUpdate);
    }
}
