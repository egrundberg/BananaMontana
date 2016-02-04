/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id2212.p1.acme.model;

/**
 *
 * @author Erik Grundberg
 * @author Milan Stojanovic
 *
 */
public interface CustomerDTO {

    /**
     *
     * @return
     */
    public String getUsername();

    /**
     *
     * @return
     */
    public String getPassword();

    /**
     *
     * @return
     */
    public Boolean getBanned();

    /**
     *
     * @return
     */
    public Integer getPurchaseAmount();

    /**
     *
     * @return
     */
    public Integer getBalance();

    /**
     *
     * @param balance
     */
    public void setBalance(Integer balance);

    /**
     *
     * @param amount
     */
    public void setPurchaseAmount(Integer amount);

    /**
     *
     * @param username
     */
    public void setUsername(String username);

    /**
     *
     * @param password
     */
    public void setPassword(String password);

    /**
     *
     * @param banned
     */
    public void setBanned(Boolean banned);
}
