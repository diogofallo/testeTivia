package com.apiplanosaude.controller;


import com.apiplanosaude.dto.BeneficiarioDto;
import com.apiplanosaude.dto.DocumentoDto;
import com.apiplanosaude.entity.Documento;
import com.apiplanosaude.exception.ResourceNotFoundException;
import com.apiplanosaude.response.Response;
import com.apiplanosaude.service.DocumentoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/plano-saude/documento/")
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @Autowired
    ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(DocumentoController.class);


    @PostMapping()
    public ResponseEntity<Response> cadastraDocumento(@Valid @RequestBody DocumentoDto documentoDTO) throws Exception {
        Response response = new Response();
        try{
            documentoService.create(documentoDTO);
        }catch (ResourceNotFoundException ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setMsgRetorno("Documento do Beneficiário cadastrado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response>updateDocumento(@PathVariable("id") Long id,
               @Valid @RequestBody DocumentoDto documentoDto) throws Exception {
        Response response = new Response();
        try{
            documentoService.update(id, documentoDto);
        }catch (ResourceNotFoundException ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setMsgRetorno("Documento alterado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping(value = "/listaDocumentos/{beneficiarioId}")
    public ResponseEntity<List<DocumentoDto>> listarBeneficiarios(@PathVariable("beneficiarioId") Long beneficiarioId) {
        log.info("Buscando todos documentos por beneficiários");
        Response response = new Response();
        List<DocumentoDto> dados = new ArrayList<DocumentoDto>();
        try {
            dados = documentoService.buscarDocumentosBeneficiario(beneficiarioId);
        }catch (Exception ex) {
            log.error("Erro --> " + ex.getMessage());
            return new ResponseEntity<List<DocumentoDto>>(dados,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<DocumentoDto>>(dados,HttpStatus.OK);
    }

}
