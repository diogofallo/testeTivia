package com.apiplanosaude.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Response{

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer codigoRetorno;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String msgRetorno;

	public Response() {
	}

}
