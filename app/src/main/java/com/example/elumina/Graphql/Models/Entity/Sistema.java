package com.example.elumina.Graphql.Models.Entity;

public class Sistema {
    public String Id;
    public String NombrePlanta;
    public String NumeroPaneles;
    public String ModeloInversor;
    public String CapacidadInversor;
    public double LatUbicacion;
    public double LonUbicacion;
    public Boolean Activo;
    public String FechaInstalacion;
    public String CapacidadTotal;
    public String ProveedorId;
    public String ClienteId;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombrePlanta() {
        return NombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        NombrePlanta = nombrePlanta;
    }

    public String getNumeroPaneles() {
        return NumeroPaneles;
    }

    public void setNumeroPaneles(String numeroPaneles) {
        NumeroPaneles = numeroPaneles;
    }

    public String getModeloInversor() {
        return ModeloInversor;
    }

    public void setModeloInversor(String modeloInversor) {
        ModeloInversor = modeloInversor;
    }

    public String getCapacidadInversor() {
        return CapacidadInversor;
    }

    public void setCapacidadInversor(String capacidadInversor) {
        CapacidadInversor = capacidadInversor;
    }

    public double getLatUbicacion() {
        return LatUbicacion;
    }

    public void setLatUbicacion(double latUbicacion) {
        LatUbicacion = latUbicacion;
    }

    public double getLonUbicacion() {
        return LonUbicacion;
    }

    public void setLonUbicacion(double lonUbicacion) {
        LonUbicacion = lonUbicacion;
    }

    public Boolean getActivo() {
        return Activo;
    }

    public void setActivo(Boolean activo) {
        Activo = activo;
    }

    public String getFechaInstalacion() {
        return FechaInstalacion;
    }

    public void setFechaInstalacion(String fechaInstalacion) {
        FechaInstalacion = fechaInstalacion;
    }

    public String getCapacidadTotal() {
        return CapacidadTotal;
    }

    public void setCapacidadTotal(String capacidadTotal) {
        CapacidadTotal = capacidadTotal;
    }

    public String getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(String proveedorId) {
        ProveedorId = proveedorId;
    }

    public String getClienteId() {
        return ClienteId;
    }

    public void setClienteId(String clienteId) {
        ClienteId = clienteId;
    }
}
