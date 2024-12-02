package com.example.elumina.Graphql.Models.Entity;

public class HistorialRendimiento {

    public String id ;
    public double Generacion;
    public double ConsumoRed;
    public double ConsumoTotal;
    public String FechaRegistro;
    public String RendimientoId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getGeneracion() {
        return Generacion;
    }

    public void setGeneracion(double generacion) {
        Generacion = generacion;
    }

    public double getConsumoRed() {
        return ConsumoRed;
    }

    public void setConsumoRed(double consumoRed) {
        ConsumoRed = consumoRed;
    }

    public double getConsumoTotal() {
        return ConsumoTotal;
    }

    public void setConsumoTotal(double consumoTotal) {
        ConsumoTotal = consumoTotal;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public String getRendimientoId() {
        return RendimientoId;
    }

    public void setRendimientoId(String rendimientoId) {
        RendimientoId = rendimientoId;
    }
}
