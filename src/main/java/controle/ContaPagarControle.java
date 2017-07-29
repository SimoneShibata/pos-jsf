/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.BaixaContaPagar;
import entidade.ContaPagar;
import entidade.PessoaJuridica;
import facade.ContaPagarFacade;
import facade.PessoaJuridicaFacade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author UniCesumar
 */
@ManagedBean
@SessionScoped
public class ContaPagarControle  implements Serializable {

    private ContaPagar contaPagar;
    private Date hoje = new Date();
    
    @EJB
    private ContaPagarFacade contaPagarFacade;
    private MoneyConverter moneyConverter;
    private BaixaContaPagar baixaContaPagar;
    @EJB
    private PessoaJuridicaFacade pessoaJuridicaFacade;
    private ConverterGenerico converterPessoa;
    
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
        Double total = baixaContaPagar.getValor() + contaPagar.getValorBaixado();
        if (total <= contaPagar.getValor() ) {
            baixaContaPagar.setContaPagar(contaPagar);
            contaPagar.getListaBaixa().add(baixaContaPagar);
            baixaContaPagar = new BaixaContaPagar();            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não é possível fazer baixa de valor superior ao valor da conta a pagar", "Erro"));
        }
    }

    public Date getHoje() {
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }
    
    public List<PessoaJuridica> listaPessoas(String parte) {
        return pessoaJuridicaFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterGenerico(pessoaJuridicaFacade);
        }
        return converterPessoa;
    }

    public void setConverterPessoa(ConverterGenerico converterPessoa) {
        this.converterPessoa = converterPessoa;
    }
    
}
