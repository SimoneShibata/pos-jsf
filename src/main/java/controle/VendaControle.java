/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.ContaReceber;
import entidade.ItensVenda;
import entidade.Pessoa;
import entidade.Produto;
import entidade.Venda;
import facade.PessoaFacade;
import facade.ProdutoFacade;
import facade.VendaFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class VendaControle implements Serializable {

    private Venda venda;
    private ItensVenda itensVenda;
    @EJB
    private VendaFacade vendaFacade;
    @EJB
    private PessoaFacade pessoaFacade;
    private ConverterGenerico converterPessoa;
    private MoneyConverter moneyConverter;
    @EJB
    private ProdutoFacade produtoFacade;
    private ConverterGenerico converterProduto;
    private Date dataPrimeiraParcela;
    
    public void gerarParcelas() {
        venda.setListaContasReceber(new ArrayList<ContaReceber>());
        Date dataParcela = dataPrimeiraParcela;
        for (int i = 1; i <= venda.getNumeroParcelas(); i++) {
            ContaReceber cr = new ContaReceber();
            cr.setDataLancamento(venda.getDataVenda());
            cr.setVenda(venda);
            cr.setPessoa(venda.getPessoa());
            cr.setValor(venda.getValorTotal()/venda.getNumeroParcelas());
            cr.setParcela(i);
            cr.setVencimento(dataParcela);
            venda.getListaContasReceber().add(cr);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataParcela);
            cal.add(Calendar.MONTH, 1);
            dataParcela = cal.getTime();
        }
    }
    
    public void addItem(){
        Double estoque = itensVenda.getProduto().getEstoque();
        for (ItensVenda it : venda.getItensVendas()) {
            if (itensVenda.getProduto().equals(it.getProduto())) {
                estoque = estoque - it.getQuantidade();
            }
        }
        
        if (estoque >= itensVenda.getQuantidade()) {
            Boolean existeNaLista = false;
            for (ItensVenda iv : venda.getItensVendas()) {
                if (iv.getProduto().equals(itensVenda.getProduto())) {
                    iv.setQuantidade(iv.getQuantidade() + itensVenda.getQuantidade());
                    iv.setPreco(itensVenda.getPreco());
                    existeNaLista = true; 
                }
            }
            if (!existeNaLista) {
                itensVenda.setVenda(venda);
                venda.getItensVendas().add(itensVenda);
            }
            itensVenda = new ItensVenda();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Estoque insuficiente", "Restam apenas" + estoque + " unidades"));
        }
    }
    
    public void removerItem(ItensVenda it){
        it.setVenda(null);
        venda.getItensVendas().remove(it);
    }
    
    public void atualizaPreco(){
        itensVenda.setPreco(itensVenda.getProduto().getPreco());
    }
    
    public List<Produto> listaProdutos(String parte){
        return produtoFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterProduto() {
        if(converterProduto == null){
            converterProduto = new ConverterGenerico(produtoFacade);
        }
        return converterProduto;
    }

    public void setConverterProduto(ConverterGenerico converterProduto) {
        this.converterProduto = converterProduto;
    }
    
    
    
    public ItensVenda getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(ItensVenda itensVenda) {
        this.itensVenda = itensVenda;
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

    public List<Pessoa> listaPessoas(String parte) {
        return pessoaFacade.listaFiltrando(parte, "nome");
    }

    public ConverterGenerico getConverterPessoa() {
        if (converterPessoa == null) {
            converterPessoa = new ConverterGenerico(pessoaFacade);
        }
        return converterPessoa;
    }

    public void setConverterPessoa(ConverterGenerico converterPessoa) {
        this.converterPessoa = converterPessoa;
    }

    public List<Venda> listaTodos() {
        return vendaFacade.listaTodos();
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Date getDataPrimeiraParcela() {
        return dataPrimeiraParcela;
    }

    public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
        this.dataPrimeiraParcela = dataPrimeiraParcela;
    }
    
    

    public void salvar() {
        vendaFacade.salvar(venda);
    }

    public void novo() {
        venda = new Venda();
        itensVenda = new ItensVenda();
    }

    public void excluir(Venda e) {
        vendaFacade.remover(e);
    }

    public void alterar(Venda e) {
        this.venda = e;
        itensVenda = new ItensVenda();
    }

}
