package com.apiplanosaude.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="beneficiario")
public class Beneficiario implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @Column(name = "data_inclusao", nullable = false)
    private String dataInclusao;

    @Column(name = "data_atualizacao")
    private String dataAtualizacao;

}
