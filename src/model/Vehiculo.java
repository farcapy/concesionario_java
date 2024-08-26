package model;

public class Vehiculo {

    private String id_vehiculo, matricula, marca, modelo, year_fab, precio, color, tipo_motor, transmision, km, ubicacion, estado, descripcion;

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }        

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getYear_fab() {
        return year_fab;
    }

    public void setYear_fab(String year_fab) {
        this.year_fab = year_fab;
    }

    public String getMarcaModeloYear() {
        return marca + " " + modelo + " (" + year_fab + ")";
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo_motor() {
        return tipo_motor;
    }

    public void setTipo_motor(String tipo_motor) {
        this.tipo_motor = tipo_motor;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    @Override
//    public String toString() {
//        return "Vehiculo { " +
//                "Marca: '" + marca + '\'' +
//                ", Modelo: '" + modelo + '\'' +
//                ", Año: " + year_fab +
//                ", Precio: '" + precio + '\'' +
//                " }";
//    }
}
