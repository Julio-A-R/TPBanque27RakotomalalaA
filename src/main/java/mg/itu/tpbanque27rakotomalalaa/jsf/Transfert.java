/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.jsf;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import mg.itu.tpbanque27rakotomalalaa.ejb.GestionnaireCompte;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;
import mg.itu.tpbanque27rakotomalalaa.jsf.util.Util;

/**
 *
 * @author 27_rakotomalala
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert {
    private Long idSource;
    private Long idDestinataire;
    private int montant;

    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }

    public Long getIdSource() {
        return idSource;
    }

    public Long getIdDestinataire() {
        return idDestinataire;
    }

    public int getMontant() {
        return montant;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public void setIdDestinataire(Long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String transferer() {
        boolean erreur = false;
        CompteBancaire source = this.gestionnaire.getCompteById(idSource);
        if (montant <= 0) {
            Util.messageErreur("le montant doit etre > 0 ", "Montant incorrect", "form:montant");
        }
        if (source == null) { //aucun source
            Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:source");
            erreur = true;
        } else {
            if (source.getSolde() < montant) { //solde du compte source est insuffisant...
                Util.messageErreur("Votre solde est insuffisant !", "Solde insuffisant", "form:montant");
                erreur = true;
            }
        }

        CompteBancaire destinataire = this.gestionnaire.getCompteById(idDestinataire);
        if (destinataire == null) { //aucun source
            Util.messageErreur("Aucun compte avec cet id !", "Aucun compte avec cet id !", "form:destinataire");
            erreur = true;
        }

        if (erreur) {
            return null;
        } else {
            
            this.gestionnaire.transferer(source, destinataire, montant);
            Util.addFlashInfoMessage("Tranfert correctement effectué : " 
                    + source.getNom()
                    + " a transféré "
                    + montant
                    + " à " + destinataire.getNom());
            return "listeComptes?faces-redirect=true";
        }
    }

}