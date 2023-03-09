package com.example.appcinefilos;

import java.io.Serializable;

public class MovieModelClass  implements Serializable {
    String id_pelicula;
    String nombre;
    String imagen;
    String genero;
    String duracion;
    String categoria;
     String sinopsis;
     String clasificacion_edad;
     String[] lenguaje;
     String dimension;

    public MovieModelClass(String id_pelicula, String nombre, String imagen, String genero, String duracion, String categoria, String sinopsis, String clasificacion_edad, String[] lenguaje, String dimension) {
        this.id_pelicula = id_pelicula;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
        this.duracion = duracion;
        this.categoria = categoria;
        this.sinopsis = sinopsis;
        this.clasificacion_edad = clasificacion_edad;
        this.lenguaje = lenguaje;
        this.dimension = dimension;
    }

    public MovieModelClass(String id_pelicula, String nombre, String imagen, String genero, String duracion, String categoria) {
        this.id_pelicula = id_pelicula;
        this.nombre = nombre;
        this.imagen = imagen;
        this.genero = genero;
        this.duracion = duracion;
        this.categoria = categoria;
    }


    public MovieModelClass() {
    }



    public String getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(String id_pelicula) {
        this.id_pelicula = id_pelicula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getClasificacion_edad() {
        return clasificacion_edad;
    }

    public void setClasificacion_edad(String clasificacion_edad) {
        this.clasificacion_edad = clasificacion_edad;
    }

    public String[] getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String[] lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
