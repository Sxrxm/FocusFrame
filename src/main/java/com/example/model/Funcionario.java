package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
public class Funcionario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_funcionario")
    private Long idFuncionario;


    @Column(nullable = false, name = "especialidad")
    private String especialidad;


    @Column(nullable = false, name = "experiencia")
    private String experiencia;

    @Column(nullable = false, name = "licencia")
    private String licencia;

    @Column(nullable = false, name = "estado")
    private Boolean estado;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_usuario")
    private User user;

}
