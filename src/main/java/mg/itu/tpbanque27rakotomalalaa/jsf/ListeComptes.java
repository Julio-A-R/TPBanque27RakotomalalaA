/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import mg.itu.tpbanque27rakotomalalaa.ejb.GestionnaireCompte;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;
import mg.itu.tpbanque27rakotomalalaa.jsf.util.Util;

/**
 *
 * @author 27_rakotomalala
 */
@Named
@ViewScoped
public class ListeComptes implements Serializable{
    
    @EJB
    private GestionnaireCompte gestionnaireCompte;
  
    private List<CompteBancaire> allComptes;
    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }
    
    public List<CompteBancaire> getAllComptes() {
        if (allComptes == null) {
            allComptes = gestionnaireCompte.getAllComptes();
        }
        return allComptes;
    }
    
    public String supprimer(CompteBancaire c){
        gestionnaireCompte.deleteCompte(c);
        Util.addFlashInfoMessage("Compte de " + c.getNom() + " supprimé");
        return "listeComptes?faces-redirect=true";
    }
}
