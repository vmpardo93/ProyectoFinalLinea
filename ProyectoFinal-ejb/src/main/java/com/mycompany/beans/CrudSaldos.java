/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.controladorEntity.SaldosJpaController;
import com.mycompany.entity.Saldos;
import javax.ejb.Stateless;

/**
 *
 * @author fetec
 */
@Stateless
public class CrudSaldos implements CrudSaldosLocal {

    @Override
    public void agregarSaldo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarSaldo(Saldos s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarSaldo(int i) {
        try{
            SaldosJpaController jpa=new SaldosJpaController();
            jpa.destroy(i);
        }catch(Exception ex){
            System.out.println("Ha ocurrido un error");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
