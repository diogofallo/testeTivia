package com.apiplanosaude.repository;

import com.apiplanosaude.entity.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    @Query(value = "select * from documento  where beneficiario_id = :beneficiarioId and tipo_documento = :tipoDocumento",
            nativeQuery = true)
    Documento findByDocumentoBeneficiario(@Param("beneficiarioId")Long beneficiarioId,
                                          @Param("tipoDocumento") String tipoDocumento);

    List<Documento> findByBeneficiarioId(Long beneficiarioId);

}
