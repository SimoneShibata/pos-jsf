/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidade.Compra;
import entidade.ItemCompra;
import entidade.Produto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UniCesumar
 */
@Stateless
public class CompraFacade extends AbstractFacade<Compra> {

    @PersistenceContext(name = "aulajsfposjavaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Compra> listaTodos() {
        List<Compra> compras = super.listaTodos();
        for(Compra c : compras) {
            c.getItensCompra().size();
            c.getListaContasPagar().size();
        }
        return compras;
    }
    
    public CompraFacade() {
        super(Compra.class);
    }

    @Override
    public void salvar(Compra compra) {
        baixarEstoque(compra);
        em.merge(compra);
    }
    
    private void baixarEstoque(Compra compra) {
        for (ItemCompra item: compra.getItensCompra()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
            em.merge(produto);
        }
    }
    
}
