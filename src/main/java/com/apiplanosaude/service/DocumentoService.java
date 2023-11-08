package com.apiplanosaude.service;

import com.apiplanosaude.converter.DozerConverter;
import com.apiplanosaude.dto.BeneficiarioDto;
import com.apiplanosaude.dto.DocumentoDto;
import com.apiplanosaude.entity.Beneficiario;
import com.apiplanosaude.entity.Documento;
import com.apiplanosaude.exception.ResourceNotFoundException;
import com.apiplanosaude.repository.BeneficiarioRepository;
import com.apiplanosaude.repository.DocumentoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository repository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    private static final Logger log = LoggerFactory.getLogger(BeneficiarioService.class);

    public DocumentoDto create(DocumentoDto documento) throws ParseException {

        String tipoDocumento = documento.getTipoDocumento().contains("RG") ? "0" : "1";
        Optional<Beneficiario> beneficiario = buscarBeneficiario(documento.getBeneficiarioId());

        if (!beneficiario.isPresent())
            throw new ResourceNotFoundException("Beneficiário " + documento.getBeneficiarioId() + " não encontrado!");

        if(buscarDoumentoBeneficiario((documento.getBeneficiarioId()),
                tipoDocumento).isPresent())
            throw new ResourceNotFoundException("Documento do Beneficiário já cadastrado");


        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        documento.setBeneficiarioId(documento.getBeneficiarioId());
        documento.setDataInclusao(formatDate.format(data));
        documento.setDataAtualizacao(null);

        var entity = DozerConverter.parseObject(documento, Documento.class);
        var vo = DozerConverter.parseObject(repository.save(entity), DocumentoDto.class);
        return vo;
    }

    public DocumentoDto update(Long id, DocumentoDto documento) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado!"));

        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");

        entity.setDescricao(documento.getDescricao());
        entity.setDataAtualizacao(formatDate.format(data));
        var vo = DozerConverter.parseObject(repository.save(entity), DocumentoDto.class);
        return vo;
    }

    public List<DocumentoDto> buscarDocumentosBeneficiario(Long beneficiarioId) {
        log.info("Busca documentos do beneficiário :" + beneficiarioId);
        return DozerConverter.parseListObjects(repository.findByBeneficiarioId(beneficiarioId), DocumentoDto.class);
    }

    public Optional<Documento> buscarDoumentoBeneficiario(Long beneficiarioId, String tipoDocumento) {
        log.info("Busca documeto beneficiario  {}", beneficiarioId);
        return Optional.ofNullable(this.repository.findByDocumentoBeneficiario(beneficiarioId, tipoDocumento));
    }

    public Optional<Beneficiario> buscarBeneficiario(Long beneficiarioId) {
        log.info("Busca  beneficiario  {}", beneficiarioId);
        return Optional.ofNullable(this.beneficiarioRepository.findByBeneficiarioId(beneficiarioId));
    }

}
