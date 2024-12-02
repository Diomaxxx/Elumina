package com.example.elumina.Graphql.Models.Entity;

public class Rendimiento {
    public String Id;
    public double Generacion;
    public double ConsumoRed;
    public double ConsumoTotal;
    public String SistemaId;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getSistemaId() {
        return SistemaId;
    }

    public void setSistemaId(String sistemaId) {
        SistemaId = sistemaId;
    }
}
