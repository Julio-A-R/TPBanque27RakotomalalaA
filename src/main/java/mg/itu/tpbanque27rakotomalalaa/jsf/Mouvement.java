/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import mg.itu.tpbanque27rakotomalalaa.ejb.GestionnaireCompte;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;
import mg.itu.tpbanque27rakotomalalaa.jsf.util.Util;

/**
 *
 * @author 27_rakotomalala
 */
@Named(value = "mouvement")
@ViewScoped
public class Mouvement implements Serializable {
    private Long id;
    private String typeMouvement;
    @Positive
    private int montant;
    private CompteBancaire compte;

    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of Mouvement
     */
    public Mouvement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void loadCompte(){
        this.compte = gestionnaire.getCompteById(id);
    }

    public String enregistrer(){
        try {
            if(typeMouvement.equals("ajout")){
                gestionnaire.deposer(compte, montant);
            }
            else if(typeMouvement.equals("retrait")){
                if(montant > compte.getSolde()){
                    Util.messageErreur("Votre solde est insuffisant !", "Solde insuffisant", "form:montant");
                    return null;
                }
                gestionnaire.retirer(compte, montant);
            }
            Util.addFlashInfoMessage(typeMouvement + " enregistré sur le compte de " + compte.getNom());
            return "listeComptes?faces-redirect=true";
        } catch (EJBException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte de " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !");
                } else { // ou bien afficher le message de ex...
                    Util.messageErreur(cause.getMessage());
                }
            }
            return null;
        }
    }

}