/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.entity.Saldos;
import com.mycompany.entity.Usuarios;
import javax.ejb.Local;

/**
 *
 * @author fetec
 */
@Local
public interface CrudSaldosLocal {
    
    public void agregarSaldo();
    public void editarSaldo(Saldos s);
    public void eliminarSaldo(int i);
}
