package com.jonurq.workshoptucuman.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String title;
    private String amount;
    private String id;
    private String fecha;
    private String status;
    private String imagen;


    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public Item(String title, String amount, String imagen, String status, String id, String fecha) {
        this.title = title;
        this.amount = amount;
        this.imagen = imagen;
        this.status = status;
        this.id = id;
        this.fecha = fecha;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
