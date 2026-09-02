package org.example.javafxmenuretos;

public class LoteCafe {

    private String codigo;
    private String productor;
    private String municipio;
    private double peso;
    private String calidad;

    public LoteCafe(
            String codigo,
            String productor,
            String municipio,
            double peso,
            String calidad
    ) {
        this.codigo = codigo;
        this.productor = productor;
        this.municipio = municipio;
        this.peso = peso;
        this.calidad = calidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProductor() {
        return productor;
    }

    public String getMunicipio() {
        return municipio;
    }

    public double getPeso() {
        return peso;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }
}