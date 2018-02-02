package be.phw.serv.repository;

import be.phw.serv.domain.BonCommande;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BonCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonCommandeRepository extends JpaRepository<BonCommande, Long> {

}
