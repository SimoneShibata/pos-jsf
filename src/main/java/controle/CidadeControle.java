/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import entidade.Cidade;
import facade.CidadeFacade;
import facade.EstadoFacade;
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
public class CidadeControle {

    private Cidade cidade;
    private ConverterGenerico estadoConverter;
    
    @EJB
    private CidadeFacade cidadeFacade;
    @EJB
    private EstadoFacade estadoFacade;
    
    public ConverterGenerico getEstadoConverter() {
        if(estadoConverter == null){
            estadoConverter = new ConverterGenerico(estadoFacade);
        }
        return estadoConverter;
    }
    
    public List<Cidade> listaTodos() {
        return cidadeFacade.listaTodos();
    }

    public void setEstadoConverter(ConverterGenerico estadoConverter) {
        this.estadoConverter = estadoConverter;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public void salvar() {
        cidadeFacade.salvar(cidade);
    }

    public void novo() {
        cidade = new Cidade();
    }

    public void excluir(Cidade e) {
        cidadeFacade.remover(e);
    }

    public void alterar(Cidade e) {
        this.cidade = e;
    }

}
