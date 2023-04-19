/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.persistence.OptimisticLockException;
import mg.itu.tpbanque27rakotomalalaa.ejb.GestionnaireCompte;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;
import mg.itu.tpbanque27rakotomalalaa.jsf.util.Util;

/**
 *
 * @author 27_rakotomalala
 */
@Named(value = "edit")
@ViewScoped
public class Edit implements Serializable {
    private Long id;
    private CompteBancaire compte;

    @EJB
    GestionnaireCompte gestionnaireCompte;

    /**
     * Creates a new instance of Edit
     */
    public Edit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void loadCompte(){
        this.compte = gestionnaireCompte.getCompteById(id);
    }

    public String editCompte(){
        try {

            if (this.gestionnaireCompte.checkExistingName(compte)) {
                Util.messageErreur("Nom est déjà utilisé !", "Nom est déjà utilisé !", "form:nom");
                return null;
            }
            gestionnaireCompte.updateCompte(compte);
            Util.addFlashInfoMessage("Le compte numero " + compte.getId() + " a été modifié");
            return "listeComptes?faces-redirect=true";
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !");
                } else { // Afficher le message de ex si la cause n'est pas une OptimisticLockException
                    Util.messageErreur(cause.getMessage());
                }
            } else { // Pas de cause attachée à l'EJBException
                Util.messageErreur(ex.getMessage());
            }
            return null; // pour rester sur la page s'il y a une exception
        }
    }

}
