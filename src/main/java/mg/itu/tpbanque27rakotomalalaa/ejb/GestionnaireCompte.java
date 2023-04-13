/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanque27rakotomalalaa.ejb;

import jakarta.annotation.sql.DataSourceDefinition;
import mg.itu.tpbanque27rakotomalalaa.entities.CompteBancaire;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Gère la persistance des CompteBancaire.
 */
@DataSourceDefinition (
    className="com.mysql.cj.jdbc.MysqlDataSource",
    name="java:app/jdbc/banque",
    serverName="localhost",
    portNumber=3306,
    user="root",
    password="Rakotomalala", 
    databaseName="banque",
    properties = {
      "useSSL=false",
      "allowPublicKeyRetrieval=true"
    }
)
@Stateless
public class GestionnaireCompte {
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    
    public void creerCompte(CompteBancaire compte) {
       em.persist(compte);
    }
    
    public List<CompteBancaire> getAllComptes() {
       TypedQuery query = 
            em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }
    
    public long nbComptes() {
        TypedQuery<Long> query = 
            em.createQuery("select count(c) from CompteBancaire c", Long.class);
        return query.getSingleResult();
    }
    
    public void transferer(CompteBancaire source,
            CompteBancaire destinataire,
            int montant){
        source.retirer(montant);
        destinataire.deposer(montant);
        em.merge(source);
        em.merge(destinataire);
    }

    public CompteBancaire getCompteById(int id){
        return em.find(CompteBancaire.class, Long.valueOf(id));
    }
}