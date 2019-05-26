/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.controladorEntity.HistorialJpaController;
import com.mycompany.entity.Historial;
import javax.ejb.Stateless;

/**
 *
 * @author fetec
 */
@Stateless
public class CrudHistorial implements CrudHistorialLocal {

    @Override
    public void agregarHistorial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarHistorial(Historial u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarHistorial(int i) {
        try{
            HistorialJpaController jpa=new HistorialJpaController();
            jpa.destroy(i);
        }catch(Exception ex){
            System.out.println("Ha ocurrido un error");
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
