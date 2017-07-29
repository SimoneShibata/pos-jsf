/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidade.Estado;
import entidade.GrupoProduto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UniCesumar
 */
@Stateless
public class GrupoProdutoFacade extends AbstractFacade<GrupoProduto> {

    @PersistenceContext(name = "aulajsfposjavaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public GrupoProdutoFacade() {
        super(GrupoProduto.class);
    }
}
