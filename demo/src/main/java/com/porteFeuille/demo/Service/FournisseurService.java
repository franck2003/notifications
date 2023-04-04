package com.porteFeuille.demo.Service;

import java.util.Optional;

import com.porteFeuille.demo.Serveur.Entity.Entity_table.Fournisseur;
import com.porteFeuille.demo.Serveur.Repositories.FournisseurRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@Service
public class FournisseurService {


    @Autowired
    private FournisseurRepositories fournisseurRepository;

    public FournisseurService(FournisseurRepositories repository) {
        this.fournisseurRepository = repository;
    }

    //@Query("select * from fournisseur")
    public Optional<Fournisseur> getFournisseur(final Long id) {
        return fournisseurRepository.findById(id);
    }


    public void deleteFournisseur(final Long id) {
        fournisseurRepository.deleteById(id);
    }

    public Fournisseur saveFournisseur(@RequestBody Fournisseur fournisseur){
        try {
            Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
            return savedFournisseur;
        }
        catch(DataIntegrityViolationException e) {
            System.out.println(fournisseur.getEmail() + "est déja présent dans la BDD");
        }
        return null;
    }

    public Iterable<Fournisseur> getFournisseurs(){
        return fournisseurRepository.findAll();
    }
}
