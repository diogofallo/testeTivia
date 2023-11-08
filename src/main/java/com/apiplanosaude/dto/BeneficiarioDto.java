package com.apiplanosaude.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;

@Data

public class BeneficiarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    private String nome;

    private String cpf;

    private String telefone;

    private String dataNascimento;

    private String dataInclusao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dataAtualizacao;

}
