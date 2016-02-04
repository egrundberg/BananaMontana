/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A class that represents a gnome type in a users basket in the APG Web Shop
 * 
 * @author Erik
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findBasketGnome",
            query = "SELECT b.id "
            + "FROM Basket b "
            + "WHERE b.customer = :cus "
            + "AND b.color = :col"),
    @NamedQuery(name = "findBasketGnomes",
            query = "SELECT b.id "
            + "FROM Basket b "
            + "WHERE b.customer = :cus"),
    @NamedQuery(name = "countRows",
            query = "SELECT b.id "
            + "FROM Basket b"),
    @NamedQuery(name = "emptyBasket",
            query = "DELETE FROM Basket b "
            + "WHERE b.customer = :cus"),})
public class Basket implements BasketDTO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String customer;
    private String color;
    private Integer amount;
    private Integer totalPrice;

    public Basket() {
    }

    /**
     * Generates a basket object
     *
     * @param customer The name of the customer
     * @param color The color of the gnome
     * @param amount The amount of gnomes
     * @param id The id of the gnome
     * @param totalPrice The total price for all gnomes of that color
     */
    public Basket(String customer, String color, Integer amount, Integer id, Integer totalPrice) {
        this.customer = customer;
        this.color = color;
        this.amount = amount;
        this.id = id;
        this.totalPrice = totalPrice;
    }
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">

    /**
     *
     * @return
     */
    @Override
    public String getCustomer() {
        return customer;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getAmount() {
        return amount;
    }

    /**
     *
     * @return
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @param totalPrice
     */
    @Override
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     *
     * @param color
     */
    @Override
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @param customer
     */
    @Override
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     *
     * @param amount
     */
    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    //</editor-fold>
    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (!Objects.equals(id, "") ? id.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Basket)) {
            return false;
        }
        Basket other = (Basket) object;
        return !((Objects.equals(this.id, "") && !Objects.equals(other.id, "")) || (!Objects.equals(this.id, "") && !this.id.equals(other.id)));
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "se.kth.id2212.p1.acme.model.Basket[ id=" + id + " ]";
    }

}
