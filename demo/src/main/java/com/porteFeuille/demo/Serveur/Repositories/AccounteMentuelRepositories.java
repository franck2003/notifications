package com.porteFeuille.demo.Serveur.Repositories;

import com.porteFeuille.demo.Serveur.Entity.Entity_table.AccounteMentuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.Optional;

public interface AccounteMentuelRepositories extends JpaRepository<AccounteMentuel, Long>, JpaSpecificationExecutor<AccounteMentuel> {
    @Query("""
            select a from AccounteMentuel a
            where a.montant = ?1 and a.date_facturation = ?2 and a.date_echeance = ?3 and a.status_paiement = ?4""")
    Optional<AccounteMentuel> findByMontantAndDate_facturationAndDate_echeanceAndStatus_paiement(Long montant, @NonNull Date date_facturation, @NonNull Date date_echeance, @NonNull String status_paiement);
}
