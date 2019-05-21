/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controladorEntity;

import com.mycompany.controladorEntity.exceptions.NonexistentEntityException;
import com.mycompany.controladorEntity.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entity.Roles;
import com.mycompany.entity.Saldos;
import com.mycompany.entity.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 *
 * @author fetec
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController() {
        //this.utx = utx;
        this.emf = Persistence.createEntityManagerFactory("com.mycompany_ProyectoFinal-ejb_ejb_1.0-SNAPSHOTPU").createEntityManager();
    }
    private UserTransaction utx = null;
    private EntityManager emf = null;

    public EntityManager getEntityManager() {
        return emf;
    }

    public void create(Usuarios usuarios) throws RollbackFailureException, Exception {
        if (usuarios.getSaldosList() == null) {
            usuarios.setSaldosList(new ArrayList<Saldos>());
        }
        
        try {
            emf.getTransaction().begin();
            emf.persist(usuarios);
            emf.getTransaction().commit();
        }catch (Exception ex) {            
            throw ex;
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuarios());
            Roles idRolOld = persistentUsuarios.getIdRol();
            Roles idRolNew = usuarios.getIdRol();
            List<Saldos> saldosListOld = persistentUsuarios.getSaldosList();
            List<Saldos> saldosListNew = usuarios.getSaldosList();
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRoles());
                usuarios.setIdRol(idRolNew);
            }
            List<Saldos> attachedSaldosListNew = new ArrayList<Saldos>();
            for (Saldos saldosListNewSaldosToAttach : saldosListNew) {
                saldosListNewSaldosToAttach = em.getReference(saldosListNewSaldosToAttach.getClass(), saldosListNewSaldosToAttach.getIdSaldos());
                attachedSaldosListNew.add(saldosListNewSaldosToAttach);
            }
            saldosListNew = attachedSaldosListNew;
            usuarios.setSaldosList(saldosListNew);
            usuarios = em.merge(usuarios);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getUsuariosList().remove(usuarios);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getUsuariosList().add(usuarios);
                idRolNew = em.merge(idRolNew);
            }
            for (Saldos saldosListOldSaldos : saldosListOld) {
                if (!saldosListNew.contains(saldosListOldSaldos)) {
                    saldosListOldSaldos.setIdUsuario(null);
                    saldosListOldSaldos = em.merge(saldosListOldSaldos);
                }
            }
            for (Saldos saldosListNewSaldos : saldosListNew) {
                if (!saldosListOld.contains(saldosListNewSaldos)) {
                    Usuarios oldIdUsuarioOfSaldosListNewSaldos = saldosListNewSaldos.getIdUsuario();
                    saldosListNewSaldos.setIdUsuario(usuarios);
                    saldosListNewSaldos = em.merge(saldosListNewSaldos);
                    if (oldIdUsuarioOfSaldosListNewSaldos != null && !oldIdUsuarioOfSaldosListNewSaldos.equals(usuarios)) {
                        oldIdUsuarioOfSaldosListNewSaldos.getSaldosList().remove(saldosListNewSaldos);
                        oldIdUsuarioOfSaldosListNewSaldos = em.merge(oldIdUsuarioOfSaldosListNewSaldos);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuarios();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuarios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Roles idRol = usuarios.getIdRol();
            if (idRol != null) {
                idRol.getUsuariosList().remove(usuarios);
                idRol = em.merge(idRol);
            }
            List<Saldos> saldosList = usuarios.getSaldosList();
            for (Saldos saldosListSaldos : saldosList) {
                saldosListSaldos.setIdUsuario(null);
                saldosListSaldos = em.merge(saldosListSaldos);
            }
            em.remove(usuarios);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }
    
    public Usuarios validarLogin(String usuario, String contrasena){
        
        try{
            Usuarios u= new Usuarios();
            TypedQuery<Usuarios>consultaUser=emf.createNamedQuery("Usuarios.findByNombreDeUsuario", Usuarios.class);
            consultaUser.setParameter("nombreDeUsuario", usuario);
            u = consultaUser.getSingleResult();
            if(u.getClave().equals(contrasena)){
                return u;
            }else{
                return null;
            }
            
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
