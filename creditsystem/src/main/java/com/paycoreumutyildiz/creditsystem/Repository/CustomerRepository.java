package com.paycoreumutyildiz.creditsystem.Repository;

import com.paycoreumutyildiz.creditsystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("select c from Customer c where c.phoneNumber =:phoneNumber")
    Customer findByPhoneNumber(String phoneNumber);
}
