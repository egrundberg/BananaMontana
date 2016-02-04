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
public interface BasketDTO {

    /**
     *
     * @return
     */
    public String getCustomer();

    /**
     *
     * @return
     */
    public String getColor();

    /**
     *
     * @return
     */
    public Integer getAmount();

    /**
     *
     * @return
     */
    public Integer getTotalPrice();

    /**
     *
     * @param totalPrice
     */
    public void setTotalPrice(Integer totalPrice);

    /**
     *
     * @param color
     */
    public void setColor(String color);

    /**
     *
     * @param customer
     */
    public void setCustomer(String customer);

    /**
     *
     * @param amount
     */
    public void setAmount(Integer amount);

}
