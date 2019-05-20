/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controladorEntity;

import com.mycompany.controladorEntity.exceptions.NonexistentEntityException;
import com.mycompany.controladorEntity.exceptions.RollbackFailureException;
import com.mycompany.entity.Roles;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.entity.Usuarios;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author fetec
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) throws RollbackFailureException, Exception {
        if (roles.getUsuariosList() == null) {
            roles.setUsuariosList(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Usuarios> attachedUsuariosList = new ArrayList<Usuarios>();
            for (Usuarios usuariosListUsuariosToAttach : roles.getUsuariosList()) {
                usuariosListUsuariosToAttach = em.getReference(usuariosListUsuariosToAttach.getClass(), usuariosListUsuariosToAttach.getIdUsuarios());
                attachedUsuariosList.add(usuariosListUsuariosToAttach);
            }
            roles.setUsuariosList(attachedUsuariosList);
            em.persist(roles);
            for (Usuarios usuariosListUsuarios : roles.getUsuariosList()) {
                Roles oldIdRolOfUsuariosListUsuarios = usuariosListUsuarios.getIdRol();
                usuariosListUsuarios.setIdRol(roles);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
                if (oldIdRolOfUsuariosListUsuarios != null) {
                    oldIdRolOfUsuariosListUsuarios.getUsuariosList().remove(usuariosListUsuarios);
                    oldIdRolOfUsuariosListUsuarios = em.merge(oldIdRolOfUsuariosListUsuarios);
                }
            }
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

    public void edit(Roles roles) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Roles persistentRoles = em.find(Roles.class, roles.getIdRoles());
            List<Usuarios> usuariosListOld = persistentRoles.getUsuariosList();
            List<Usuarios> usuariosListNew = roles.getUsuariosList();
            List<Usuarios> attachedUsuariosListNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosListNewUsuariosToAttach : usuariosListNew) {
                usuariosListNewUsuariosToAttach = em.getReference(usuariosListNewUsuariosToAttach.getClass(), usuariosListNewUsuariosToAttach.getIdUsuarios());
                attachedUsuariosListNew.add(usuariosListNewUsuariosToAttach);
            }
            usuariosListNew = attachedUsuariosListNew;
            roles.setUsuariosList(usuariosListNew);
            roles = em.merge(roles);
            for (Usuarios usuariosListOldUsuarios : usuariosListOld) {
                if (!usuariosListNew.contains(usuariosListOldUsuarios)) {
                    usuariosListOldUsuarios.setIdRol(null);
                    usuariosListOldUsuarios = em.merge(usuariosListOldUsuarios);
                }
            }
            for (Usuarios usuariosListNewUsuarios : usuariosListNew) {
                if (!usuariosListOld.contains(usuariosListNewUsuarios)) {
                    Roles oldIdRolOfUsuariosListNewUsuarios = usuariosListNewUsuarios.getIdRol();
                    usuariosListNewUsuarios.setIdRol(roles);
                    usuariosListNewUsuarios = em.merge(usuariosListNewUsuarios);
                    if (oldIdRolOfUsuariosListNewUsuarios != null && !oldIdRolOfUsuariosListNewUsuarios.equals(roles)) {
                        oldIdRolOfUsuariosListNewUsuarios.getUsuariosList().remove(usuariosListNewUsuarios);
                        oldIdRolOfUsuariosListNewUsuarios = em.merge(oldIdRolOfUsuariosListNewUsuarios);
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
                Integer id = roles.getIdRoles();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getIdRoles();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<Usuarios> usuariosList = roles.getUsuariosList();
            for (Usuarios usuariosListUsuarios : usuariosList) {
                usuariosListUsuarios.setIdRol(null);
                usuariosListUsuarios = em.merge(usuariosListUsuarios);
            }
            em.remove(roles);
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

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
