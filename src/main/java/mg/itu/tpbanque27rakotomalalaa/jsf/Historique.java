/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import mg.itu.tpbanque27rakotomalalaa.ejb.GestionnaireCompte;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;

/**
 *
 * @author RakotomalalaA
 */
@Named(value = "historique")
@ViewScoped
public class Historique implements Serializable{
    private Long id;
    private CompteBancaire compte;

    @EJB
    GestionnaireCompte gestionnaire;

    public void loadCompte(){
        this.compte = gestionnaire.getCompteById(id);
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

}