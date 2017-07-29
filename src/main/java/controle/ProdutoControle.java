/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import converter.MoneyConverter;
import entidade.GrupoProduto;
import entidade.Produto;
import facade.GrupoProdutoFacade;
import facade.ProdutoFacade;
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
public class ProdutoControle {

    private Produto produto;

    @EJB
    private ProdutoFacade produtoFacade;
        
    @EJB
    private GrupoProdutoFacade grupoProdutoFacade;
    
    private ConverterGenerico grupoProdutoConverter;
    private MoneyConverter moneyConverter;

    public MoneyConverter getMoneyConverter() {
        if (moneyConverter == null) {
            moneyConverter = new MoneyConverter();
        }
        return moneyConverter;
    }

    public void setMoneyConverter(MoneyConverter moneyConverter) {
        this.moneyConverter = moneyConverter;
    }
   
    public List<GrupoProduto> listaGrupos(String parte) {
        return grupoProdutoFacade.listaFiltrando(parte, "nome");
    }
    
    public ConverterGenerico getGrupoProdutoConverter() {
        if (grupoProdutoConverter == null) {
            grupoProdutoConverter = new ConverterGenerico(grupoProdutoFacade);
        }
        return grupoProdutoConverter;
    }
    
    public List<Produto> listaTodos() {
        return produtoFacade.listaTodos();
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto p) {
        this.produto = p;
    }

    public void salvar() {
        produtoFacade.salvar(produto);
    }

    public void novo() {
        produto = new Produto();
    }

    public void excluir(Produto p) {
        produtoFacade.remover(p);
    }

    public void alterar(Produto p) {
        this.produto = p;
    }  

    public ProdutoFacade getProdutoFacade() {
        return produtoFacade;
    }

    public void setProdutoFacade(ProdutoFacade produtoFacade) {
        this.produtoFacade = produtoFacade;
    }

    public GrupoProdutoFacade getGrupoProdutoFacade() {
        return grupoProdutoFacade;
    }

    public void setGrupoProdutoFacade(GrupoProdutoFacade grupoProdutoFacade) {
        this.grupoProdutoFacade = grupoProdutoFacade;
    }

    
}
