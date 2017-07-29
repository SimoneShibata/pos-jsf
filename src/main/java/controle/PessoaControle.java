/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import converter.ConverterGenerico;
import entidade.Cidade;
import entidade.Pessoa;
import entidade.PessoaFisica;
import entidade.PessoaJuridica;
import facade.CidadeFacade;
import facade.PessoaFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PessoaControle implements Serializable {

    @ManagedProperty(value = "#{pessoaFisicaControle}")
    private PessoaFisicaControle pessoaFisicaControle;

    @ManagedProperty(value = "#{pessoaJuridicaControle}")
    private PessoaJuridicaControle pessoaJuridicaControle;
    
    @EJB
    private CidadeFacade cidadeFacade;

    private ConverterGenerico cidadeConverter;

    public List<Cidade> listaCidades(String parte) {
        return cidadeFacade.listaFiltrando(parte, "nome");
    }
    
    public ConverterGenerico getCidadeConverter() {
        if (cidadeConverter == null) {
            cidadeConverter = new ConverterGenerico(cidadeFacade);
        }
        return cidadeConverter;
    }
    
    public void setPessoaFisicaControle(PessoaFisicaControle pessoaFisicaControle) {
        this.pessoaFisicaControle = pessoaFisicaControle;
    }

    public void setPessoaJuridicaControle(PessoaJuridicaControle pessoaJuridicaControle) {
        this.pessoaJuridicaControle = pessoaJuridicaControle;
    }

    @EJB
    private PessoaFacade pessoaFacade;

    public String alterarPessoa(Pessoa p) {
        if (p instanceof PessoaFisica) {
            pessoaFisicaControle.setPessoaFisica((PessoaFisica) p);
            return "pessoafisicaedita";
        }
        pessoaJuridicaControle.setPessoaJuridica((PessoaJuridica) p);
        return "pessoajuridicaedita";
    }

    public void excluirPessoa(Pessoa p) {
        if (p instanceof PessoaFisica) {
            pessoaFisicaControle.excluir((PessoaFisica) p);
        }
        pessoaJuridicaControle.excluir((PessoaJuridica) p);
    }

    public List<Pessoa> listaTodos() {
        return pessoaFacade.listaTodos();
    }

}
