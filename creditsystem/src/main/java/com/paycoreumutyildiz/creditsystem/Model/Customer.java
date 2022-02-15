package com.paycoreumutyildiz.creditsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Table(name = "customer")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "sid")
    @NotNull(message = "TC kimlik numarası boş olamaz!")
    private long sid;

    @Column(name = "customer_name")
    @NotBlank(message = "İsim boş olamaz!")
    private String customerName;

    @Column(name = "surname")
    @NotBlank(message = "Soyisim boş olamaz!")
    private String surname;

    @Column(name = "salary")
    @NotNull(message = "Maaş bilgisi boş olamaz!")
    private long salary;

    @Column(name = "phone_number")
    @NotBlank(message = "Telefon numarası boş olamaz!")//unique ekle
    private String phoneNumber;

    @Column(name = "credit_score")
    @NotNull(message = "İsim boş olamaz!")
    private Integer creditScore;
}
