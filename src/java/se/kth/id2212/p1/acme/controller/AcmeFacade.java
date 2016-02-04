/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.controller;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import se.kth.id2212.p1.acme.model.Basket;
import se.kth.id2212.p1.acme.model.BasketDTO;
import se.kth.id2212.p1.acme.model.Customer;
import se.kth.id2212.p1.acme.model.CustomerDTO;
import se.kth.id2212.p1.acme.model.Gnome;
import se.kth.id2212.p1.acme.model.GnomeDTO;

/**
 * The controller layer for the APG Web Shop
 * 
 * @author Erik
 *
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AcmeFacade {

    @PersistenceContext(unitName = "bankPU")
    private EntityManager em;

    // <editor-fold defaultstate="collapsed" desc="CUSTOMER MANAGEMENT *">

    /**
     *  Store a new customer in the PU, if it does not already exist.
     * 
     * @param username The name of the user
     * @param password The password of the user
     * @return a new custumer if they dont exist
     */
    public CustomerDTO createCustomerDTO(String username, String password) {
        CustomerDTO customer = em.find(Customer.class, username);
        if (customer == null) {
            customer = new Customer(username, password);
            em.persist(customer);
            return customer;
        } else {
            return null;
        }
    }

    /**
     * Retrun customer if the username and password match
     * @param username The name of the user
     * @param password The password of the user
     * @return customer
     */
    public CustomerDTO loginCustomer(String username, String password) {
        List<String> i = em.createNamedQuery("findName")
                .setParameter("acc", username)
                .setParameter("pw", password)
                .getResultList();
        if (i.isEmpty()) {
            return null;
        }
        CustomerDTO found = em.find(Customer.class, i.get(0));
        return found;
    }

    /**
     * Find a customer in the PU
     * 
     * @param username The name of the user
     * @return customer
     */
    public CustomerDTO findCustomer(String username) {
        List<String> i = em.createNamedQuery("findCustomer")
                .setParameter("acc", username)
                .getResultList();
        if (i.isEmpty()) {
            return null;
        }
        CustomerDTO found = em.find(Customer.class, i.get(0));
        return found;
    }

    /**
     * Sets the banned property of a user to true
     * 
     * @param username The name of the user
     * @return if the customer was found
     */
    public Boolean ban(String username) {
        CustomerDTO customer = em.find(Customer.class, username);
        if (customer == null) {
            return false;
        } else {
            customer.setBanned(true);
            em.merge(customer);
            return true;
        }
    }
    
    /**
     * Sets the banned property of a user to false
     * 
     * @param username The name of the user
     * @return if the customer was found
     */
    public Boolean unban(String username) {
        CustomerDTO customer = em.find(Customer.class, username);
        if (customer == null) {
            return false;
        } else {
            customer.setBanned(false);
            em.merge(customer);
            return true;
        }
    }

    // </editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Buy/Sell *">

    /**
     * Get all gnomes
     * 
     * @return all gnomes in the inventory
     */
    public List<GnomeDTO> getGnomes() {
        List<Integer> list = em.createNamedQuery("findGnomes").getResultList();
        if (list.isEmpty()) {
            return null;
        }
        List<GnomeDTO> gnomes = new ArrayList<>();
        for (Integer gnomeID : list) {
            gnomes.add(em.find(Gnome.class, gnomeID));
        }
        return gnomes;
    }

    /**
     * Add a new gnome to the customers basket
     * 
     * @param username The name of the user
     * @param currentAmount Amount of the new gnome
     * @param currentColor Color of the new gnome
     * @return a updated list with all basket items
     */
    public List<BasketDTO> addToBasket(String username, Integer currentAmount, String currentColor) {
        if (updateInventory(currentColor, currentAmount, false) == true) {
            List<Integer> list = em.createNamedQuery("findBasketGnome")
                    .setParameter("cus", username)
                    .setParameter("col", currentColor)
                    .getResultList();
            Integer aprice = (Integer) em.createNamedQuery("findGnomePrice")
                    .setParameter("col", currentColor)
                    .getSingleResult();
            aprice = aprice * currentAmount;
            BasketDTO basketitem;
            if (!list.isEmpty()) {
                basketitem = em.find(Basket.class, list.get(0));
                basketitem.setAmount(basketitem.getAmount() + currentAmount);
                basketitem.setTotalPrice(basketitem.getTotalPrice() + aprice);
                em.merge(basketitem);
            } else {
                List<Integer> id = em.createNamedQuery("countRows").getResultList();
                if (id.isEmpty()) {
                    basketitem = new Basket(username, currentColor, currentAmount, 1, aprice);
                } else {
                    int size = id.size();
                    size++;
                    basketitem = new Basket(username, currentColor, currentAmount, size, aprice);
                }
                em.persist(basketitem);
            }
        }
        return getBasket(username);
    }

    /**
     * Updates the amount of gnomes in the inventory
     * 
     * @param color The color of the new gnomes
     * @param amount The amount of the new gnomes
     * @param add True for adding gnomes and vice versa
     * @return if the inventory was updated
     */
    public boolean updateInventory(String color, Integer amount, boolean add) {
        List<Integer> list = em.createNamedQuery("findColorGnomes")
                .setParameter("col", color)
                .getResultList();
        if (list.isEmpty()) {
            return false;
        }
        int newAmount;
        GnomeDTO gnomeItem = em.find(Gnome.class, list.get(0));
        if (add == true) {
            newAmount = gnomeItem.getAmount() + amount;
        } else {
            newAmount = gnomeItem.getAmount() - amount;
        }
        if (newAmount < 0) {
            return false;
        } else {
            gnomeItem.setAmount(newAmount);
            em.merge(gnomeItem);
            return true;
        }
    }

    /**
     * Get the id of the last type of gnome
     * 
     * @return the ID of the last type of gnome
     */
    public Integer getLastGnomeID() {
        List<Integer> id = em.createNamedQuery("findGnomes").getResultList();
        return id.get(id.size() - 1);
    }

    /**
     * Returns the basket of a specific customer
     * 
     * @param username The name of the user
     * @return a list of all basktet items
     */
    public List<BasketDTO> getBasket(String username) {
        List<BasketDTO> basket;
        basket = new ArrayList<>();
        List<Integer> i = em.createNamedQuery("findBasketGnomes")
                .setParameter("cus", username)
                .getResultList();
        if (i.isEmpty()) {
            return basket;
        }

        for (Integer id : i) {
            basket.add(em.find(Basket.class, id));
        }
        return basket;
    }

    /**
     * Returns the basket items to the inventory
     * 
     * @param username The name of the user
     * @return a null basket
     */
    public List<BasketDTO> returnBasket(String username) {
        List<Integer> list = em.createNamedQuery("findBasketGnomes")
                .setParameter("cus", username)
                .getResultList();
        BasketDTO basketgnome;
        for (int i = 0; i < list.size(); i++) {
            basketgnome = em.find(Basket.class, list.get(i));
            updateInventory(basketgnome.getColor(), basketgnome.getAmount(), true);
        }
        emptyBasket(username);
        return null;
    }

    /**
     * Get the total price of all items in the basket
     * 
     * @param basket A list of basktet items
     * @return total
     */
    public Integer getTotal(List<BasketDTO> basket) {
        int total = 0;
        for (BasketDTO basketDTO : basket) {
            total = total + basketDTO.getTotalPrice();
        }
        return total;
    }

    /**
     * Withdraws the total basket price if the user has enough money and empties the basket
     * 
     * @param customer A custmomer object to perform operations on
     * @param total The total price of all items
     * @return if the purchase was made or not
     */
    public boolean buy(CustomerDTO customer, Integer total) {
        int balance = customer.getBalance();
        if (total > balance) {
            return false;
        } else {
            balance = balance - total;
            customer.setBalance(balance);
            em.merge(customer);
            emptyBasket(customer.getUsername());
            return true;
        }
    }

    /**
     * Empties the basket without returning the items to the inventory
     * 
     * @param username The name of the user
     * @return null 
     */
    public List<BasketDTO> emptyBasket(String username) {
        em.createNamedQuery("emptyBasket")
                .setParameter("cus", username)
                .executeUpdate();
        return null;
    }

    /**
     * Add a new gnomes to the inventory
     * 
     * @param currentColor The color of the new gnomes
     * @param currentAmount The amount of the new gnomes
     * @param currentPrice The new price of the gnome (null will keep the old price)
     * @return the updated list of gnomes
     */
    public List<GnomeDTO> addGnome(String currentColor, Integer currentAmount, Integer currentPrice) {
        List<Integer> list = em.createNamedQuery("findColorGnomes").setParameter("col", currentColor).getResultList();
        GnomeDTO gnome;
        if (list.isEmpty()) {
            gnome = new Gnome(getLastGnomeID() + 1, currentColor, currentPrice, currentAmount);
            em.persist(gnome);
        } else {
            gnome = em.find(Gnome.class, list.get(0));
            gnome.setAmount(gnome.getAmount() + currentAmount);
            if (currentPrice != null) {
                gnome.setPrice(currentPrice);
            }
            em.merge(gnome);
        }
        return getGnomes();
    }

    /**
     * Removes a specified type of gnome
     * 
     * @param currentColor The color of the gnome to be removed
     * @return the updated list of gnomes
     */
    public List<GnomeDTO> removeGnome(String currentColor) {
        List<Integer> list = em.createNamedQuery("findColorGnomes").setParameter("col", currentColor).getResultList();
        if (list.isEmpty()) {
            return getGnomes();
        } else {
            GnomeDTO gnome = em.find(Gnome.class, list.get(0));
            em.remove(gnome);
            return getGnomes();
        }
    }

}

//</editor-fold>
