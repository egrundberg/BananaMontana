/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A class that represents a customer in the APG Web Shop
 *
 * @author Erik
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "findName",
            query = "SELECT c.username "
            + "FROM Customer c "
            + "WHERE c.username = :acc "
            + "AND c.password = :pw"
    ),
    @NamedQuery(name = "findCustomer",
            query = "SELECT c.username "
            + "FROM Customer c "
            + "WHERE c.username = :acc "),
    @NamedQuery(name = "deleteUser",
            query = "DELETE FROM Customer c "
            + "WHERE c.username = :cus")
})

public class Customer implements CustomerDTO, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;

    private String password;

    private Boolean loggedIn;
    private Boolean banned;

    private Integer balance;
    private Integer purchaseAmount;

    public Customer() {

    }

    /**
     * Generates a customer object that has a balance of 1000$
     *
     * @param username The name of the user
     * @param password The password of the user
     */
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = true;
        this.banned = false;
        this.balance = 1000;
        this.purchaseAmount = 0;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean getBanned() {
        return banned;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getPurchaseAmount() {
        return purchaseAmount;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getBalance() {
        return balance;
    }

    /**
     *
     * @param amount
     */
    @Override
    public void setBalance(Integer amount) {
        balance = amount;
    }

    /**
     *
     * @param amount
     */
    @Override
    public void setPurchaseAmount(Integer amount) {
        this.purchaseAmount = amount;
    }

    /**
     *
     * @param username
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param banned
     */
    @Override
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new Integer(username).hashCode();
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        return this.username.equals(other.username);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "se.kth.id2212.p1.acme.model.Customer[ id=" + username + "]";
    }
}
