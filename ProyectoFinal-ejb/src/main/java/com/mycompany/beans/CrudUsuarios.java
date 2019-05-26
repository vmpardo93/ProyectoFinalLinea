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


    @Override
    public void agregarUsuario() {
        try{
            UsuariosJpaController jpa= new UsuariosJpaController();
            Usuarios u=new Usuarios();
            u.setNombre("Juan");
            u.setNombreUsuario("dark");
            u.setClave("1234");
            u.setCorreo("prueba@prueba.com");
            u.setRutaFoto("3254ty6e5grsd");
            u.setIdRol(1);
            u.setEstado(1);
            u.setToken("defrg674543");
            jpa.create(u);
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    @Override
    public Usuarios validarLogin(String user, String contrasena) {
        UsuariosJpaController jpa= new UsuariosJpaController();
        Usuarios u=new Usuarios();
        u=jpa.validarLogin(user, contrasena);
        if(u!=null){
            return u;
        }else
            return null;
    }

    @Override
    public void editarUsuario(Usuarios u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarUsuario(int i) {
        try{
            UsuariosJpaController jpa=new UsuariosJpaController();
            jpa.destroy(i);
        }catch(Exception ex){
            System.out.println("Ha ocurrido un error");
        }
    }
}
