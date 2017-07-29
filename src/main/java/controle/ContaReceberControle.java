/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.MoneyConverter;
import entidade.BaixaContaReceber;
import entidade.ContaReceber;
import facade.ContaReceberFacade;
import java.util.List;
import java.util.Objects;
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
    private MoneyConverter moneyConverter;
    private BaixaContaReceber baixaContaReceber;
    
    public boolean getSituacaoPago(ContaReceber cr) {
        if(Objects.equals(cr.getValorBaixado(), cr.getValor())) {
            return true;
        }
        return false;
    }
    public boolean getSituacaoAberto(ContaReceber cr) {
        if(cr.getValorBaixado() < cr.getValor()) {
            return true;
        }
        return false;
    }
    
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
        baixaContaReceber = new BaixaContaReceber();
    }

    public MoneyConverter getMoneyConverter() {
        if (moneyConverter == null) {
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }

    public void setMoneyConverter(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
    }

    public BaixaContaReceber getBaixaContaReceber() {
        return baixaContaReceber;
    }

    public void setBaixaContaReceber(BaixaContaReceber baixaContaReceber) {
        this.baixaContaReceber = baixaContaReceber;
    }
    
    public void baixar() {
        baixaContaReceber.setContaReceber(contaReceber);
        contaReceber.getListaBaixa().add(baixaContaReceber);
        baixaContaReceber = new BaixaContaReceber();
    }
    
}
