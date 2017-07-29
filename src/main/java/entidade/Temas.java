/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidade;

/**
 *
 * @author JaimeDias
 */
public enum Temas {

    afterdark("afterdark"),
    afternoon("afternoon"),
    afterwork("afterwork"),
    aristo("aristo"),   
    blitzer("blitzer"),
    bluesky("bluesky"),
    bootstrap("bootstrap"),
    casablanca("casablanca"),
    cruze("cruze"),
    cupertino("cupertino"),        
    eggplant("eggplant"),    
    flick("flick"),    
    home("home"),
    humanity("humanity"),
    midnight("midnight"),
    overcast("overcast"),
    redmond("redmond"),
    rocket("rocket"),
    sam("sam"),
    smoothness("smoothness"),
    start("start"),
    sunny("sunny"),
    trontastic("trontastic"),
    vader("vader");
    private String descricao;

    private Temas(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
