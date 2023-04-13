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

/**
 *
 * @author 27_rakotomalala
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert {
    private int idSource;
    private int idDestinataire;
    private int montant;

    @EJB
    GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }

    public int getIdSource() {
        return idSource;
    }

    public int getIdDestinataire() {
        return idDestinataire;
    }

    public int getMontant() {
        return montant;
    }

    public void setIdSource(int idSource) {
        this.idSource = idSource;
    }

    public void setIdDestinataire(int idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public String transferer(){
        CompteBancaire source = this.gestionnaire.getCompteById(idSource);
        CompteBancaire destinataire = this.gestionnaire.getCompteById(idDestinataire);
        this.gestionnaire.transferer(source, destinataire, montant);
        return "listeComptes?faces-redirect=true";
    }

}