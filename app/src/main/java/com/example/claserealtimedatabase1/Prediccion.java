package com.example.claserealtimedatabase1;

public class Prediccion {

    private double codigo;
    private String nombre;
    private long stock;
    private double venta;

    public Prediccion() {
        //Es obligatorio incluir constructor por defecto
    }

    public Prediccion(double codigo,String nombre, long stock, double venta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.venta = venta;
    }

    public double getCodigo() {
        return codigo;
    }

    public void setCodigo(double codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "Prediccion{" + "codigo='" + codigo + '\'' + ", nombre="+ nombre+",, stock=" + stock + ", venta=" + venta + '}';
    }
}
