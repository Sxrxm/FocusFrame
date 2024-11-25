package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Time;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id_paciente")
    private Long idPaciente;


    @Column(nullable = false, name = "telefono")
    private int telefono;

    @Column(nullable = false, name = "fecha_nacimiento")
    private Time fechaNacimiento;

    @Column(nullable = false, name = "documento")
    private int documento;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "id_usuario")
    private User user;

}
