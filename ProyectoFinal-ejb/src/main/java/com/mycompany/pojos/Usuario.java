/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.pojos;

import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author fetec
 */
@Stateless
public class Usuario implements UsuarioLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private Integer id;
    private String usuario;
    private String clave;
    private String correo;

    public Usuario() {
    }

    public Usuario(Integer id,String usuario, String clave, String correo) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.correo = correo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios= new ArrayList<>();
        return usuarios;
    }

    @Override
    public void setUsuarios(ArrayList<Usuario> usuarios) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario getUsuarioEspecifico(int id) {
        ArrayList<Usuario> usuarios= new ArrayList<>();;
        for(Usuario user: usuarios){
            if(user.getId()==id){
                return user;
            }            
        }
        return null;
    }
}
