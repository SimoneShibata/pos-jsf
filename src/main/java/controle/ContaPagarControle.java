/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.MoneyConverter;
import entidade.BaixaContaPagar;
import entidade.ContaPagar;
import entidade.ContaReceber;
import facade.ContaPagarFacade;
import java.io.Serializable;
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
public class ContaPagarControle  implements Serializable {

    private ContaPagar contaPagar;

    @EJB
    private ContaPagarFacade contaPagarFacade;
    private MoneyConverter moneyConverter;
    private BaixaContaPagar baixaContaPagar;
    
    public boolean getSituacaoPago(ContaPagar cr) {
        if(Objects.equals(cr.getValorBaixado(), cr.getValor())) {
            return true;
        }
        return false;
    }
    public boolean getSituacaoAberto(ContaPagar cr) {
        if(cr.getValorBaixado() < cr.getValor()) {
            return true;
        }
        return false;
    }
    
    public List<ContaPagar> listaTodos() {
        return contaPagarFacade.listaTodos();
    }
    
    public ContaPagar getContaPagar() {
        return contaPagar;
    }

    public void setContaPagar(ContaPagar contaPagar) {
        this.contaPagar = contaPagar;
    }

    public void salvar() {
        contaPagarFacade.salvar(contaPagar);
    }

    public void novo() {
        contaPagar = new ContaPagar();
    }

    public void excluir(ContaPagar e) {
        contaPagarFacade.remover(e);
    }

    public void alterar(ContaPagar e) {
        this.contaPagar = e;
        baixaContaPagar = new BaixaContaPagar();
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

    public BaixaContaPagar getBaixaContaPagar() {
        return baixaContaPagar;
    }

    public void setBaixaContaPagar(BaixaContaPagar baixaContaPagar) {
        this.baixaContaPagar = baixaContaPagar;
    }
    
    public void baixar() {
        baixaContaPagar.setContaPagar(contaPagar);
        contaPagar.getListaBaixa().add(baixaContaPagar);
        baixaContaPagar = new BaixaContaPagar();
    }
    
}
