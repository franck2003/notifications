package com.porteFeuille.demo.Serveur.Entity.Entity_table;

import jakarta.persistence.Transient;
public class ResetPwd {

    private String motDePasse;
    private String nouveau_mdp;
    private String confirm_mdp;

    public ResetPwd() {
    }

    public ResetPwd(String motDePasse, String nouveau_mdp, String confirm_mdp) {
        this.motDePasse = motDePasse;
        this.nouveau_mdp = nouveau_mdp;
        this.confirm_mdp = confirm_mdp;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNouveau_mdp() {
        return nouveau_mdp;
    }

    public void setNouveau_mdp(String nouveau_mdp) {
        this.nouveau_mdp = nouveau_mdp;
    }

    public String getConfirm_mdp() {
        return confirm_mdp;
    }

    public void setConfirm_mdp(String confirm_mdp) {
        this.confirm_mdp = confirm_mdp;
    }
}
