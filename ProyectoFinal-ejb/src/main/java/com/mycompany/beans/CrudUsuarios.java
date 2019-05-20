/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.beans;

import com.mycompany.controladorEntity.UsuariosJpaController;
import com.mycompany.entity.Usuarios;
import javax.ejb.Stateless;

/**
 *
 * @author fetec
 */
@Stateless
public class CrudUsuarios implements CrudUsuariosLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void agregarUsuario() {
        try{
            UsuariosJpaController jpa= new UsuariosJpaController();
            Usuarios u=new Usuarios();
            u.setApellidos("ApellidoPrueba");
            u.setClave("1234");
            u.setCorreo("prueba@prueba.com");
            u.setDocumento("1234567");            
            u.setRurtaDocumento("3124567jtygrefvdcw");
            u.setNombreDeUsuario("PruebaNombre");
            u.setTelefono("142567");
            u.setTipoDeDocumento("CC");
            jpa.create(u);
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    
}
