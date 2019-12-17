package com.vyperion.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "api_key")
public class ApiKey extends BaseApiKey{

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "key_name", nullable = false)
    private String keyName;

    @Column(name = "key_value", nullable = false)
    private String keyValue;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="userId", referencedColumnName = "id" ,nullable=false)
    private User userId;

}
