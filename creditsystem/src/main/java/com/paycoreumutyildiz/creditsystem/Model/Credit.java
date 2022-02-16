package com.paycoreumutyildiz.creditsystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "credit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credit {

    @Id
    @Column(name = "sid",insertable = true,updatable = false)
    private Long sid;

    @Column(name = "credit_result")
    private Boolean creditResult;

    @Column(name = "credit_limit")
    private Integer creditLimit;





}
