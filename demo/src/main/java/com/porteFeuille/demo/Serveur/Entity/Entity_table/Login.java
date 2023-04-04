package com.porteFeuille.demo.Serveur.Entity.Entity_table;

import jakarta.persistence.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "Login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;

    private String motDePasse;
    @Transient
    private String nouveau_mdp;
    @Transient
    private String confirm_mdp;
    @Transient
    private static Long whoIsConnect;
    public Login() {
    }

    public Login(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Login(String motDePasse, String nouveau_mdp, String confirm_mdp){
        this.motDePasse = motDePasse;
        this.nouveau_mdp = nouveau_mdp;
        this.confirm_mdp = confirm_mdp;
    }

    public Login(String email, String motDePasse, String nouveau_mdp, String confirm_mdp) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.nouveau_mdp = nouveau_mdp;
        this.confirm_mdp = confirm_mdp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static Long getWhoIsConnect() {
        return whoIsConnect;
    }

    public static void setWhoIsConnect(Long whoIsConnect) {
        Login.whoIsConnect = whoIsConnect;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}

