package com.apiplanosaude.controller;

import com.apiplanosaude.dto.BeneficiarioDto;
import com.apiplanosaude.dto.DocumentoDto;
import com.apiplanosaude.entity.Beneficiario;
import com.apiplanosaude.exception.ResourceNotFoundException;
import com.apiplanosaude.response.BeneficiarioResponse;
import com.apiplanosaude.response.Response;
import com.apiplanosaude.service.BeneficiarioService;
import jakarta.validation.Valid;
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


@RestController
@RequestMapping("/api/plano-saude/beneficiario/")
public class BeneficiarioController {

    @Autowired
    BeneficiarioService beneficiarioService;

    private static final Logger log = LoggerFactory.getLogger(BeneficiarioController.class);

    @PostMapping()
    public ResponseEntity<Response>creatBeneficiario(@Valid @RequestBody BeneficiarioDto beneficiarioDTO) throws Exception {
        Response response = new Response();
        try{
            beneficiarioService.create(beneficiarioDTO);
        }catch (ResourceNotFoundException ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setMsgRetorno("Beneficiário cadastrado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response>updateBeneficiario(@PathVariable("id") Long id,
                              @Valid @RequestBody BeneficiarioDto beneficiarioDTO) throws Exception {
        Response response = new Response();
        try{
            beneficiarioService.update(id, beneficiarioDTO);
        }catch (ResourceNotFoundException ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setMsgRetorno("Beneficiário alterado com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response>deletBeneficiario(@PathVariable("id") Long id) throws Exception {
        Response response = new Response();
        try{
            beneficiarioService.delete(id);
        }catch (ResourceNotFoundException ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.setMsgRetorno(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setMsgRetorno("Beneficiário removido com sucesso!");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/listaBeneficiarios")
    public ResponseEntity<List<BeneficiarioDto>> listarBeneficiarios() {
        log.info("Buscando todos beneficiários");
        List<BeneficiarioDto> dados = new ArrayList<BeneficiarioDto>();

        try {
            dados = beneficiarioService.buscarBeneficiarios();
        }
        catch (Exception ex) {
            log.error("Erro --> " + ex.getMessage());
            return new ResponseEntity<List<BeneficiarioDto>>(dados,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<BeneficiarioDto>>(dados,HttpStatus.OK);
    }
}
