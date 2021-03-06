/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.Endereco;
import entidade.PessoaFisica;
import facade.PessoaFisicaFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PessoaFisicaControle implements Serializable {

    private PessoaFisica pessoaFisica;

    @EJB
    private PessoaFisicaFacade pessoaFisicaFacade;
    
    public void salvar() {
        pessoaFisicaFacade.salvar(pessoaFisica);
    }

    public void novo() {
        pessoaFisica = new PessoaFisica();
        pessoaFisica.setEndereco(new Endereco());
    }

    public void excluir(PessoaFisica pessoaFisica) {
        pessoaFisicaFacade.remover(pessoaFisica);
    }

    public void alterar(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

}
