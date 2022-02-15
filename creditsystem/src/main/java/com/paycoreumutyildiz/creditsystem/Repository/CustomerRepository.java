package com.paycoreumutyildiz.creditsystem.Repository;

import com.paycoreumutyildiz.creditsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
