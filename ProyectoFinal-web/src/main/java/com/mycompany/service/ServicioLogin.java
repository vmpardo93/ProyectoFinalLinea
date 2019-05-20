/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.service;

import com.mycompany.beans.CrudUsuariosLocal;
import com.mycompany.pojos.UsuarioLocal;
import com.mycompany.utilitarios.Token;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fetec
 */
@javax.enterprise.context.RequestScoped
@Path("login")
public class ServicioLogin implements Serializable{
    
    @EJB
    UsuarioLocal usuario;
    
    @EJB
    CrudUsuariosLocal user;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user},{pass}")    
    public Response validarLogin(@PathParam("user") String user, @PathParam("pass") String pass) {
        
        if(user.equals("mechas") && pass.equals("1234")) {
                JsonObject json = Json.createObjectBuilder()
                                      .add("token-auto", Token.generarToken())
                                      .build();                
                //return Response.status(Response.Status.OK).entity(json).build();
                return Response.ok(json).build();
        } else {
                JsonObject json = Json.createObjectBuilder()
                                      .add("mensaje", "credenciales incorrectas")
                                      .build();
                return Response.status(Response.Status.UNAUTHORIZED).entity(json).build();
                
        }
    }
    
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    @Path("/{token}")    
    public Response desencriptar(@PathParam("token") String token) {
        
        //Token.imprimirEstructura(token);
        Token.imprimirBody(token);
        return Response.ok().build();                

    }
    
    @GET
    @Path("registro")    
    public Response registrar() {
        
        user.agregarUsuario();
        return Response.ok().build();                
    }
}
