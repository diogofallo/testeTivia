package com.apiplanosaude.service;

import com.apiplanosaude.converter.DozerConverter;
import com.apiplanosaude.dto.BeneficiarioDto;
import com.apiplanosaude.entity.Beneficiario;
import com.apiplanosaude.entity.Documento;
import com.apiplanosaude.exception.ResourceNotFoundException;
import com.apiplanosaude.repository.BeneficiarioRepository;
import com.apiplanosaude.repository.DocumentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BeneficiarioService {

    private static final Logger log = LoggerFactory.getLogger(BeneficiarioService.class);

    @Autowired
    private BeneficiarioRepository repository;

    @Autowired
    private DocumentoRepository documentoRepository;


    public BeneficiarioDto create(BeneficiarioDto beneficiarioDto) throws ParseException {

        Optional<Beneficiario> beneficiario = this.buscarCpfBeneficiario(beneficiarioDto.getCpf());
        if(beneficiario.isPresent())
            throw new ResourceNotFoundException("Beneficiário já cadastrado");

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        beneficiarioDto.setDataInclusao(formatDate.format(data));
        beneficiarioDto.setDataAtualizacao(null);

        var entity = DozerConverter.parseObject(beneficiarioDto, Beneficiario.class);
        var vo = DozerConverter.parseObject(repository.save(entity), BeneficiarioDto.class);
        return vo;
    }

    public BeneficiarioDto update(Long id, BeneficiarioDto beneficiario) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiário não encontrado!"));

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        entity.setNome(beneficiario.getNome());
        entity.setCpf(beneficiario.getCpf());
        entity.setDataNascimento(beneficiario.getDataNascimento());
        entity.setDataAtualizacao(formatDate.format(data));

        var vo = DozerConverter.parseObject(repository.save(entity), BeneficiarioDto.class);
        return vo;
    }

    public void delete(Long id) {
        Beneficiario entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiário não encontrado!"));
        repository.delete(entity);
    }

    public List<BeneficiarioDto> buscarBeneficiarios() {
        return DozerConverter.parseListObjects(repository.findAll(), BeneficiarioDto.class);
    }

    public Optional<Beneficiario> buscarCpfBeneficiario(String cpf) {
        log.info("Busca beneficiario  {}", cpf);
        return Optional.ofNullable(this.repository.findByCpf(cpf));
    }




}
