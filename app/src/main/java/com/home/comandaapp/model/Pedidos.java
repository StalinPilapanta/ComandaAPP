package com.home.comandaapp.model;

public class Pedidos {

    private String uid;
    private String NumerPedido;
    private String Nombre;
    private String cedula;
    private String menu;
    private String mesa;
    private String extras;

    public Pedidos() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumerPedido() {
        return NumerPedido;
    }

    public void setNumerPedido(String numerPedido) {
        NumerPedido = numerPedido;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return " NumerPedido";
    }
}
