/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import entidade.Endereco;
import entidade.PessoaJuridica;
import facade.PessoaJuridicaFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PessoaJuridicaControle implements Serializable {

    private PessoaJuridica pessoaJuridica;

    @EJB
    private PessoaJuridicaFacade pessoaJuridicaFacade;
    
    public void salvar() {
        pessoaJuridicaFacade.salvar(pessoaJuridica);
    }

    public void novo() {
        pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setEndereco(new Endereco());
    }

    public void excluir(PessoaJuridica pessoaJuridica) {
        pessoaJuridicaFacade.remover(pessoaJuridica);
    }

    public void alterar(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public PessoaJuridica getPessoaJuridica() {
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

}
