/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.Compra;
import entidade.ContaPagar;
import entidade.ItemCompra;
import entidade.PessoaJuridica;
import entidade.Produto;
import facade.CompraFacade;
import facade.PessoaJuridicaFacade;
import facade.ProdutoFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class CompraControle implements Serializable {

    private Compra compra;
    private ItemCompra itemCompra;
    @EJB
    private CompraFacade compraFacade;
    @EJB
    private PessoaJuridicaFacade pessoaJuridicaFacade;
    private ConverterGenerico converterPessoa;
    private MoneyConverter moneyConverter;
    @EJB
    private ProdutoFacade produtoFacade;
    private ConverterGenerico converterProduto;
    private Date dataPrimeiraParcela;
    private Date hoje = new Date();
    
    public void gerarParcelas() {
        compra.setListaContasPagar(new ArrayList<ContaPagar>());
        Date dataParcela = dataPrimeiraParcela;
        for (int i = 1; i <= compra.getNumeroParcelas(); i++) {
            ContaPagar cr = new ContaPagar();
            cr.setDataLancamento(compra.getDataCompra());
            cr.setCompra(compra);
            cr.setPessoa(compra.getFornecedor());
            cr.setValor(compra.getValorTotal()/compra.getNumeroParcelas());
            cr.setParcela(i);
            cr.setVencimento(dataParcela);
            compra.getListaContasPagar().add(cr);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataParcela);
            cal.add(Calendar.MONTH, 1);
            dataParcela = cal.getTime();
        }
    }
    
    public void addItem(){
        Double estoque = itemCompra.getProduto().getEstoque();
        for (ItemCompra it : compra.getItensCompra()) {
            if (itemCompra.getProduto().equals(it.getProduto())) {
                estoque = estoque - it.getQuantidade();
            }
        }
        
        Boolean existeNaLista = false;
        for (ItemCompra iv : compra.getItensCompra()) {
            if (iv.getProduto().equals(itemCompra.getProduto())) {
                iv.setQuantidade(iv.getQuantidade() + itemCompra.getQuantidade());
                iv.setPreco(itemCompra.getPreco());
                existeNaLista = true; 
            }
        }
        if (!existeNaLista) {
            itemCompra.setCompra(compra);
            compra.getItensCompra().add(itemCompra);
        }
        itemCompra = new ItemCompra();
    }
    
    public void removerItem(ItemCompra it){
        it.setCompra(null);
        compra.getItensCompra().remove(it);
    }
    
    public void atualizaPreco(){
        itemCompra.setPreco(itemCompra.getProduto().getPreco());
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

    public MoneyConverter getMoneyConverter() {
        if (moneyConverter == null) {
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }

    public void setMoneyConverter(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
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

    public List<Compra> listaTodos() {
        return compraFacade.listaTodos();
    }

    public Date getDataPrimeiraParcela() {
        return dataPrimeiraParcela;
    }

    public void setDataPrimeiraParcela(Date dataPrimeiraParcela) {
        this.dataPrimeiraParcela = dataPrimeiraParcela;
    }
    
    public void salvar() {
        compraFacade.salvar(compra);
    }

    public void novo() {
        compra = new Compra();
        itemCompra = new ItemCompra();
    }

    public void excluir(Compra e) {
        compraFacade.remover(e);
    }

    public void alterar(Compra e) {
        this.compra = e;
        itemCompra = new ItemCompra();
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ItemCompra getItemCompra() {
        return itemCompra;
    }

    public void setItemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    public Date getHoje() {
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }
    
    

}
