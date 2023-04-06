package com.porteFeuille.demo.Serveur.Entity.Entity_table;

import com.porteFeuille.demo.Serveur.Entity.Object.Adresse;
import jakarta.persistence.*;

@Entity
@Table(name = "Habitation" , uniqueConstraints= {
        @UniqueConstraint(columnNames = {"adresse", "pointFournitureId"})})
public class Habitation{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "habitationId")
    private Long habitationId;

    private String adresse;

    @ManyToOne
    @JoinColumn(name = "consommateur_id")
    private Consommateur consommateur_id;
    @Transient
    private Adresse siege;

    public Habitation() {
    }

    public Habitation( String adresse, Consommateur consommateur_id) {
        this.adresse = adresse;
        this.consommateur_id = consommateur_id;
    }

    public Habitation(Long l) {
        this.habitationId = l;
    }

    public Long getHabitationId() {
        return habitationId;
    }

    public void setHabitationId(Long habitationId) {
        this.habitationId = habitationId;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Adresse getSiege() {
        return siege;
    }

    public void setSiege(Adresse siege) {
        this.siege = siege;
    }

    public Consommateur getConsommateur_id() {
        return consommateur_id;
    }

    @Override
    public String toString() {
        return "Habitation{" +
                "habitationId=" + habitationId +
                ", consommateur_id=" + consommateur_id +
                ", siege=" + siege +
                '}';
    }

    public void setConsommateur_id(Consommateur consommateur_id) {
        this.consommateur_id = consommateur_id;
    }
}
