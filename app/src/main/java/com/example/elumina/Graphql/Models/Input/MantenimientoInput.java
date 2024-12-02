package com.example.elumina.Graphql.Models.Input;

public class MantenimientoInput {
    public String FechaProgramado;
    public String FechaRealizado;
    public String Estado;
    public String Descripcion;
    public String Tipo;
    public String SistemaId;
    public String NombreSistema;


    public String getFechaProgramado() {
        return FechaProgramado;
    }

    public void setFechaProgramado(String fechaProgramado) {
        FechaProgramado = fechaProgramado;
    }

    public String getFechaRealizado() {
        return FechaRealizado;
    }

    public void setFechaRealizado(String fechaRealizado) {
        FechaRealizado = fechaRealizado;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getSistemaId() {
        return SistemaId;
    }

    public void setSistemaId(String sistemaId) {
        SistemaId = sistemaId;
    }

    public String getNombreSistema() {
        return NombreSistema;
    }

    public void setNombreSistema(String nombreSistema) {
        NombreSistema = nombreSistema;
    }
}
