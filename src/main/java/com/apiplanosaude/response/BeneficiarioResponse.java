package com.apiplanosaude.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class BeneficiarioResponse<T> {

    private T data;

    private List<String> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer codigoRetorno;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msgRetorno;
    public BeneficiarioResponse() {
    }


    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
