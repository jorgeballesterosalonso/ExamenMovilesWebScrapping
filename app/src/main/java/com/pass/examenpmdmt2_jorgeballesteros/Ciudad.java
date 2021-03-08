package com.pass.examenpmdmt2_jorgeballesteros;

public class Ciudad {
    private String nombre;
    private Double latitud;
    private Double longitud;
    private String urlImagen;

    public Ciudad() {
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Ciudad(String nombre, Double latitud, Double longitud, String urlImagen) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.urlImagen = urlImagen;
    }
}
