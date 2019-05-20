/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fetec
 */
@Entity
@Table(name = "saldos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Saldos.findAll", query = "SELECT s FROM Saldos s"),
    @NamedQuery(name = "Saldos.findByIdSaldos", query = "SELECT s FROM Saldos s WHERE s.idSaldos = :idSaldos"),
    @NamedQuery(name = "Saldos.findBySaldoActual", query = "SELECT s FROM Saldos s WHERE s.saldoActual = :saldoActual"),
    @NamedQuery(name = "Saldos.findBySaldoTempotal", query = "SELECT s FROM Saldos s WHERE s.saldoTempotal = :saldoTempotal")})
public class Saldos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSaldos")
    private Integer idSaldos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldoActual")
    private Double saldoActual;
    @Column(name = "saldoTempotal")
    private Double saldoTempotal;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuarios")
    @ManyToOne
    private Usuarios idUsuario;

    public Saldos() {
    }

    public Saldos(Integer idSaldos) {
        this.idSaldos = idSaldos;
    }

    public Integer getIdSaldos() {
        return idSaldos;
    }

    public void setIdSaldos(Integer idSaldos) {
        this.idSaldos = idSaldos;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Double getSaldoTempotal() {
        return saldoTempotal;
    }

    public void setSaldoTempotal(Double saldoTempotal) {
        this.saldoTempotal = saldoTempotal;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSaldos != null ? idSaldos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Saldos)) {
            return false;
        }
        Saldos other = (Saldos) object;
        if ((this.idSaldos == null && other.idSaldos != null) || (this.idSaldos != null && !this.idSaldos.equals(other.idSaldos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.entity.Saldos[ idSaldos=" + idSaldos + " ]";
    }
    
}
