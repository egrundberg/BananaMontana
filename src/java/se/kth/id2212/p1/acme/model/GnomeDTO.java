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
public interface GnomeDTO {

    /**
     *
     * @return
     */
    public String getColor();

    /**
     *
     * @return
     */
    public Integer getPrice();

    /**
     *
     * @return
     */
    public Integer getAmount();

    /**
     *
     * @return
     */
    public Integer getID();

    /**
     *
     * @param id
     */
    public void setID(Integer id);

    /**
     *
     * @param price
     */
    public void setPrice(Integer price);

    /**
     *
     * @param color
     */
    public void setColor(String color);

    /**
     *
     * @param amount
     */
    public void setAmount(Integer amount);
}
