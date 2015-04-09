/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author salim
 */
@Stateless
public class FicheDescriptiveFacade extends AbstractFacade<FicheDescriptive> {
    @PersistenceContext(unitName = "jsfpersistencePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FicheDescriptiveFacade() {
        super(FicheDescriptive.class);
    }
    
}
