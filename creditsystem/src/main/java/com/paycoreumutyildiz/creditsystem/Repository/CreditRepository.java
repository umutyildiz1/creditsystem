package com.paycoreumutyildiz.creditsystem.Repository;

import com.paycoreumutyildiz.creditsystem.Model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
}
