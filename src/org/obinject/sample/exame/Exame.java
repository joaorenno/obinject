/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.sample.exame;

import java.awt.image.BufferedImage;
import java.util.Date;
import org.obinject.annotation.Coordinate;
import org.obinject.annotation.Edition;
import org.obinject.annotation.Extension;
import org.obinject.annotation.ExtractionMethod;
import org.obinject.annotation.Feature;
import org.obinject.annotation.Order;
import org.obinject.annotation.Number;
import org.obinject.annotation.Origin;
import org.obinject.annotation.Persistent;
import org.obinject.annotation.Point;
import org.obinject.annotation.Sort;
import org.obinject.annotation.Unique;

/**
 *
 * @author windows
 */
@Persistent
public class Exame {
    @Unique(number = Number.One, order = Order.First)
    private String numero;
    
    @Unique(number = Number.Two, order = Order.Second)
    private String paciente;
    
    @Unique(number = Number.Two, order = Order.First)
    private Date data = new Date();
    
    @Unique(number = Number.Two, order = Order.Third)
    @Edition(number = Number.One, order = Order.First)
    private String medico;
    
    @Sort(number = Number.One, order = Order.First)
    private String clinica;
    
    @Feature(number = Number.One, order = Order.First, method = ExtractionMethod.HistogramStatistical)
    @Feature(number = Number.Two, order = Order.First, method = ExtractionMethod.HaralickSymmetric)    
    @Feature(number = Number.Two, order = Order.Second, method = ExtractionMethod.HistogramStatistical)    
    @Feature(number = Number.Two, order = Order.Third, method = ExtractionMethod.Histogram)    
    private BufferedImage imagem;
    
    @Point(number = Number.One, order = Order.First)
    @Coordinate(number = Number.One, order = Order.First)
    @Origin(number = Number.One, order = Order.First)
    private double latitudeImagem;
    @Extension(number = Number.One, order = Order.First)
    private double latitudeExtensionImagem;
    
    @Point(number = Number.One, order = Order.Second)
    @Coordinate(number = Number.One, order = Order.Second)
    @Origin(number = Number.One, order = Order.Second)
    private double longitudeImagem;
    @Extension(number = Number.One, order = Order.Second)
    private double longitudeExtensionImagem;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getClinica() {
        return clinica;
    }

    public void setClinica(String clinica) {
        this.clinica = clinica;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }

    public double getLatitudeImagem() {
        return latitudeImagem;
    }

    public void setLatitudeImagem(double latitudeImagem) {
        this.latitudeImagem = latitudeImagem;
    }

    public double getLatitudeExtensionImagem() {
        return latitudeExtensionImagem;
    }

    public void setLatitudeExtensionImagem(double latitudeExtensionImagem) {
        this.latitudeExtensionImagem = latitudeExtensionImagem;
    }

    public double getLongitudeImagem() {
        return longitudeImagem;
    }

    public void setLongitudeImagem(double longitudeImagem) {
        this.longitudeImagem = longitudeImagem;
    }

    public double getLongitudeExtensionImagem() {
        return longitudeExtensionImagem;
    }

    public void setLongitudeExtensionImagem(double longitudeExtensionImagem) {
        this.longitudeExtensionImagem = longitudeExtensionImagem;
    }

}
