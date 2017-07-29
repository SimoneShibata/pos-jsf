/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidade.ContaReceber;
import entidade.ItensVenda;
import entidade.Produto;
import entidade.Venda;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UniCesumar
 */
@Stateless
public class VendaFacade extends AbstractFacade<Venda> {

    @PersistenceContext(name = "aulajsfposjavaPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public VendaFacade() {
        super(Venda.class);
    }

    @Override
    public void salvar(Venda venda) {
        baixarEstoque(venda);
        geraContaReceber(em.merge(venda));
    }
    
    private void baixarEstoque(Venda venda) {
        for (ItensVenda item: venda.getItensVendas()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
            em.merge(produto);
        }
    }
    
    private void geraContaReceber(Venda venda) {
        ContaReceber cr = new ContaReceber();
        cr.setDataLancamento(venda.getDataVenda());
        cr.setVencimento(venda.getDataVenda());
        cr.setValor(venda.getValorTotal());
        cr.setVenda(venda);
        em.merge(cr);
    }
    
}
