package com.porteFeuille.demo.Serveur.Entity.Entity_table;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "AccounteMentuel")
public class AccounteMentuel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long montant;

    private Date date_facturation;

    private Date date_echeance;

    private String status_paiement;

    public AccounteMentuel() {
    }

    public AccounteMentuel(Long id) {
        this.id = id;
    }

    public AccounteMentuel(Long montant, Date date_facturation, Date date_echeance, String status_paiement) {
        this.montant = montant;
        this.date_facturation = date_facturation;
        this.date_echeance = date_echeance;
        this.status_paiement = status_paiement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Date getDate_facturation() {
        return date_facturation;
    }

    public void setDate_facturation(Date date_facturation) {
        this.date_facturation = date_facturation;
    }

    public Date getDate_echeance() {
        return date_echeance;
    }

    public void setDate_echeance(Date date_echeance) {
        this.date_echeance = date_echeance;
    }

    public String getStatus_paiement() {
        return status_paiement;
    }

    public void setStatus_paiement(String status_paiement) {
        this.status_paiement = status_paiement;
    }
}
