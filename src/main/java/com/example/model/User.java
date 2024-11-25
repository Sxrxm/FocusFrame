package com.example.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;




@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre")
    private String name;


    @Column(unique = true, name = "apellido")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "contraseña")
    private String password;


    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private UserRole userRole;

}
