package com.paycoreumutyildiz.creditsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "security_user")
public class User {

    @Id
    @Column(name = "sid")
    private Long sid;

    @Size(min = 5, max = 25, message = "Kullanıcı ismi 5 ile 25 karakter arasında olmalıdır")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Size(min = 5, message = "Şifre en az 5 karakterli olmalıdır")
    @Column(name = "password")
    private String password;
}
