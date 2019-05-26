/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.controladorEntity.DivisasJpaController;
import com.mycompany.entity.Divisas;
import javax.ejb.Stateless;

/**
 *
 * @author fetec
 */
@Stateless
public class CrudDivisas implements CrudDivisasLocal {

    @Override
    public void agregarDivisa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarDivisa(Divisas d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarDivisa(int i) {
        try{
            DivisasJpaController jpa=new DivisasJpaController();
            jpa.destroy(i);
        }catch(Exception ex){
            System.out.println("Ha ocurrido un error");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
