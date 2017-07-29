/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.ContaReceber;
import facade.ContaReceberFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author UniCesumar
 */
@ManagedBean
@SessionScoped
public class ContaReceberControle {

    private ContaReceber contaReceber;

    @EJB
    private ContaReceberFacade contaReceberFacade;
    
    public List<ContaReceber> listaTodos() {
        return contaReceberFacade.listaTodos();
    }
    
    public ContaReceber getContaReceber() {
        return contaReceber;
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
    }

    public void salvar() {
        contaReceberFacade.salvar(contaReceber);
    }

    public void novo() {
        contaReceber = new ContaReceber();
    }

    public void excluir(ContaReceber e) {
        contaReceberFacade.remover(e);
    }

    public void alterar(ContaReceber e) {
        this.contaReceber = e;
    }

}
