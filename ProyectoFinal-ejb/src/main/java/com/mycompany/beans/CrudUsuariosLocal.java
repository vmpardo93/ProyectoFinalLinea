/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import javax.ejb.Local;

/**
 *
 * @author fetec
 */
@Local
public interface CrudUsuariosLocal {
    
    public void agregarUsuario();
}