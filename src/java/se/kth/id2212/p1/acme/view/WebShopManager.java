/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.view;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import se.kth.id2212.p1.acme.controller.*;
import se.kth.id2212.p1.acme.model.*;

/**
 * The view layer of the APG Web Shop
 * @author Erik
 */
@ManagedBean(name = "webShopManager")
@SessionScoped
public class WebShopManager implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    private static final long serialVersionUID = 1L;
    @EJB
    private AcmeFacade acmeFacade;
    private String currentUsername;
    private String currentPassword;
    private String error = null;
    private Exception transactionFailure;
    private CustomerDTO customer = null;
    private Integer customerBalance;

    /*
     * Inventory variables
     */
    private String currentColor;
    private Integer currentAmount;
    private Integer currentPrice;
    private List<GnomeDTO> gnomes;
    private List<BasketDTO> basket;
    private Integer total = 0;
    private Integer balance = 0;

    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getters, Setters and Constructors">
    /**
     * Creates a new instance of webShopManager
     */
    public WebShopManager() {
    }

    /**
     *
     * @return
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     *
     * @return
     */
    public String getError() {
        return error;
    }

    /**
     *
     * @return
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     *
     * @return
     */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     *
     * @return
     */
    public String getCurrentColor() {
        return currentColor;
    }

    /**
     *
     * @return
     */
    public Integer getCurrentAmount() {
        return currentAmount;
    }

    /**
     *
     * @return
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @return
     */
    public Integer getBalance() {
        return balance;
    }

    /**
     *
     * @return
     */
    public Integer getCurrentPrice() {
        return currentPrice;
    }

    /**
     *
     * @param currentPrice
     */
    public void setCurrentPrice(Integer currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     *
     * @param balance
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    /**
     *
     * @param total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @param currentAmount
     */
    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     *
     * @param currentColor
     */
    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
    }

    /**
     *
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     *
     * @param currentUsername
     */
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    /**
     *
     * @param currentPassword
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Conversation etc">
    /*
     * Stop the conversation and handle the exception e
     *
     * @param e the exception to handle
     */
    private void handleException(Exception e) {
        e.printStackTrace(System.err);
        error = e.getMessage();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Customer Management (Login/Register) *">
    /**
     * Checks whether or not a customer has provided the right username and
     * password If they are right - then the method gets the user's balance.
     *
     * @return success to "tell" in order to move to the homePage page.
     */
    public String loginCustomer() {

        try {
            customer = acmeFacade.loginCustomer(currentUsername, currentPassword);
            if (customer == null) {
                error = "Incorrect username/password.";
            } else if (customer.getBanned() == true) {
                error = "User is banned";
            } else {
                getGnomes();
                error = null;
                balance = customer.getBalance();
                return "success";
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Logs out the user
     *
     * @return the string success
     */
    public String logOut() {
        customer = null;
        error = null;
        basket = null;
        gnomes = null;
        return "success";
    }

    /**
     * Create a new customer.
     *
     * A error message is displayed if : - The password lentgh in smaller than 8
     * characters - The user name is used by another customer
     *
     * @return ""
     */
    public String createNewCustomer() {

        try {
            customer = acmeFacade.findCustomer(currentUsername);
            if (currentPassword.length() < 8) {
                error = "Error : Password length should be longer than 8 characters";
            } else if (customer != null) {
                error = "Error : Could not create user.";
                customer = null;
            } else {
                acmeFacade.createCustomerDTO(currentUsername, currentPassword);
                error = "User created";
            }
        } catch (Exception e) {
            handleException(e);
        }
        return "";
    }

    /**
     * Gets the username
     *
     * @return the customer's username
     */
    public String getUsername() {
        if (customer == null) {
            return "unsuccessful";
        } else {
            return customer.getUsername();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Shopping (Add to basket etc.)*">
    /**
     * Gets the list of gnomes in the inventory
     *
     * @return a list of gnomes
     */
    public List<GnomeDTO> getGnomes() {

        gnomes = acmeFacade.getGnomes();
        return gnomes;
    }

    /**
     * Adds a new item to the basket
     *
     * @return ""
     */
    public String addToBasket() {

        basket = acmeFacade.addToBasket(customer.getUsername(), currentAmount, currentColor);
        return "";
    }

    /**
     * Gets the user's basket
     *
     * @return a list of items in the basket
     */
    public List<BasketDTO> getBasket() {

        if (customer != null) {
            basket = acmeFacade.getBasket(customer.getUsername());
            total = acmeFacade.getTotal(basket);
        }
        return basket;
    }

    /**
     * Puts items in the basket
     *
     * @param username The name of the user
     * @return ""
     */
    public String setBasket(String username) {
        basket = acmeFacade.getBasket(username);
        return "";
    }

    /**
     * Returns the basket to the inventory
     *
     * @return ""
     */
    public String emptyBasket() {

        if (customer != null) {
            basket = acmeFacade.returnBasket(customer.getUsername());
        }
        return "";
    }

    /**
     * Enables the user to buy a gnome. It checks whether or not the customer
     * exists, withdraws the money and empties the user's basket.
     *
     * @return ""
     */
    public String buy() {

        if (customer != null) {
            if (acmeFacade.buy(customer, total)) {
                basket = acmeFacade.emptyBasket(customer.getUsername());
                balance = balance - total;
                error = null;
            } else {
                error = "Insufficient funds";
            }
        }
        return "";
    }

    /**
     * Deposits 1000 money to the customer
     *
     * @return ""
     */
    public String deposit() {

        acmeFacade.buy(customer, -1000);
        balance = balance + 1000;
        return "";
    }

//</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Admin*">
    /**
     * Checks if the username and password matches the username - Admin and
     * password - javajava
     *
     * @return "success" if the username and password match
     */
    public String loginAdmin() {
        if (currentUsername.equals("Admin") && currentPassword.equals("javajava")) {
            return "success";
        } else {
            error = "Not admin";
            return "";
        }
    }

    /**
     * Adds a new gnome to the inventory which has been specified by the admin
     *
     * @return ""
     */
    public String addGnome() {

        gnomes = acmeFacade.addGnome(currentColor, currentAmount, currentPrice);
        return "";
    }

    /**
     * Removes a gnome from the inventory
     *
     * @return ""
     */
    public String removeGnome() {

        gnomes = acmeFacade.removeGnome(currentColor);
        return "";
    }

    /**
     * Bans a specific user
     *
     * @return ""
     */
    public String banUser() {

        acmeFacade.ban(currentUsername);
        return "";
    }

    /**
     * Unbans a specific user
     *
     * @return ""
     */
    public String unbanUser() {

        acmeFacade.unban(currentUsername);
        return "";
    }

    // </editor-fold>
}
