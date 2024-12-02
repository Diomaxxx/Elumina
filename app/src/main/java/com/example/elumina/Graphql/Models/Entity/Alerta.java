package com.example.elumina.Graphql.Models.Entity;

import java.util.Date;

public class Alerta {
    public String Id;
    public String FechaDeteccion;
    public String Deteccion;
    public String Estado;
    public String SistemaId;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFechaDeteccion() {
        return FechaDeteccion;
    }

    public void setFechaDeteccion(String fechadeteccion) {
        FechaDeteccion = fechadeteccion;
    }

    public String getDeteccion() {
        return Deteccion;
    }

    public void setDeteccion(String deteccion) {
        Deteccion = deteccion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getSistemaId() {
        return SistemaId;
    }

    public void setSistemaId(String sistemaId) {
        SistemaId = sistemaId;
    }
}
