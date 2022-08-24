package com.example.claserealtimedatabase1;

public class producto1 {

    private String uid;
    private String Codigo;
    private String Producto;
    private String Stock;
    private String Costo;
    private String Venta;

    public producto1() {
    }

    public producto1(String uid, String codigo, String producto, String stock, String costo, String venta) {
        this.uid = uid;
        Codigo = codigo;
        Producto = producto;
        Stock = stock;
        Costo = costo;
        Venta = venta;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getCosto() {
        return Costo;
    }

    public void setCosto(String costo) {
        Costo = costo;
    }

    public String getVenta() {
        return Venta;
    }

    public void setVenta(String venta) {
        Venta = venta;
    }

    @Override
    public String toString() {
        return Producto;
    }
}
