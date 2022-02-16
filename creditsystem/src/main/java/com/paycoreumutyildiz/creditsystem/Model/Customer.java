package com.paycoreumutyildiz.creditsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Table(name = "customer")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "sid")
    @NotNull(message = "TC kimlik numarası boş olamaz!")
    private Long sid;

    @Column(name = "customer_name")
    @NotBlank(message = "İsim boş olamaz!")
    private String customerName;

    @Column(name = "surname")
    @NotBlank(message = "Soyisim boş olamaz!")
    private String surname;

    @Column(name = "salary")
    @NotNull(message = "Maaş bilgisi boş olamaz!")
    private Long salary;

    @Column(name = "phone_number",unique = true)
    @NotBlank(message = "Telefon numarası boş olamaz!")
    @Pattern(regexp="(^$|[0-9]{11})",message = "Telefon numarası sayılardan ve 11 karakterden oluşmalıdır!")
    private String phoneNumber;


    @Column(name = "credit_score")
    private Integer creditScore;

    @OneToOne(cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Credit credit;

}
