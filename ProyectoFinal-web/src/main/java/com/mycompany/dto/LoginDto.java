/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import com.mycompany.pojos.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Darkgrey93
 */
public class LoginDto {
    ArrayList<Usuario> usuarios;

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public Usuario getUsuarioEspecifico(int id){
        for(Usuario user: usuarios){
            if(user.getId()==id){
                return user;
            }
            
        }
        return null;
    }

    public LoginDto() {
        usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1,"mechas","1234","mechas@hotmail.com"));
    }
    
}
