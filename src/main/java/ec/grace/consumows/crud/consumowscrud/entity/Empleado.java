package ec.grace.consumows.crud.consumowscrud.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Base64;

public class Empleado {
    private Integer id_empleado;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private byte[] imagen;
    private String nombreEmpresa;
    private String nombreCargo;
    private String pro_estado;
    private Integer id_empresa;
    private Integer id_cargo;

    public Empleado() {
    }

    public Integer getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Integer id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(Integer id_empresa) {
        this.id_empresa = id_empresa;
    }

    public Integer getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(Integer id_cargo) {
        this.id_cargo = id_cargo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    @JsonProperty("imagenBase64")
    public String getImagenBase64() {
        return (imagen != null && imagen.length > 0) ? Base64.getEncoder().encodeToString(imagen) : "";
    }

    public String getPro_estado() {
        return pro_estado;
    }

    public void setPro_estado(String pro_estado) {
        this.pro_estado = pro_estado;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }
}
