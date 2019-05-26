/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.entity.Historial;
import javax.ejb.Local;

/**
 *
 * @author fetec
 */
@Local
public interface CrudHistorialLocal {
    
    public void agregarHistorial();
    public void editarHistorial(Historial u);
    public void eliminarHistorial(int i);
}
