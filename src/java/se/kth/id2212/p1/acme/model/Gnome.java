/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.model;

/**
 *
 * @author Erik
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

    /**
     * A class that represents a gnome in the APG Web Shop
     */
@Entity
@NamedQueries({
    @NamedQuery(name = "findGnomes",
            query = "SELECT g.id "
            + "FROM Gnome g"),
    @NamedQuery(name = "findColorGnomes",
            query = "SELECT g.id "
            + "FROM Gnome g "
            + "WHERE g.color = :col"),
    @NamedQuery(name = "findGnomePrice",
            query = "SELECT g.price " 
            + "FROM Gnome g "
            + "WHERE g.color = :col")
})

public class Gnome implements Serializable, GnomeDTO {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String color;
    private Integer price;
    private Integer amount;


    public Gnome() {
    }

    /**
     *  Generates a Gnome object
     * @param id The id of the gnome
     * @param color The color of the gnome
     * @param price The price of the gnome
     * @param amount The amount of gnomes
     */
    public Gnome(Integer id, String color, Integer price, Integer amount) {
        this.price = price;
        this.color = color;
        this.amount = amount;
        this.id = id;
    }

    
    @Override
    public Integer getPrice() {
        return price;
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
    public Integer getAmount() {
        return amount;
    }

    /**
     *
     * @return
     */
    @Override
    public Integer getID() {
        return id;
    }

    /**
     *
     * @param id
     */
    @Override
    public void setID(Integer id) {
        this.id = id;
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
     * @param amount
     */
    @Override
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     *
     * @param price
     */
    @Override
    public void setPrice(Integer price) {
        this.price = price;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (color != "" ? color.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.color == "" && other.color != "") || (this.color != "" && !this.color.equals(other.color))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "webShop.model.Gnome[ id=" + color + " ]";
    }

}
