/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojos;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author fetec
 */
@Local
public interface UsuarioLocal {
    
    public ArrayList<Usuario> getUsuarios();
    public void setUsuarios(ArrayList<Usuario> usuarios);
    public Usuario getUsuarioEspecifico(int id);
    
}
