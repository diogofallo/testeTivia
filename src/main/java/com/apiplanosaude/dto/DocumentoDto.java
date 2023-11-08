package com.apiplanosaude.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonPropertyOrder({ "id", "id_beneficiario", "tipoDocumento", "dataInclusao", "dataAtualizacao" })
public class DocumentoDto implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    private String tipoDocumento;

    private String descricao;

    private String dataInclusao;

    private Long beneficiarioId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dataAtualizacao;


}
