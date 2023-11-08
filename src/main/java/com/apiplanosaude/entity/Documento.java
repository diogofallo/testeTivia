package com.apiplanosaude.entity;

import com.apiplanosaude.entity.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@Table(name="documento")
public class Documento implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "data_inclusao", nullable = false)
    private String dataInclusao;

    @Column(name = "data_atualizacao")
    private String dataAtualizacao;

    @Column(name = "beneficiario_id")
    private Long beneficiarioId;
}
