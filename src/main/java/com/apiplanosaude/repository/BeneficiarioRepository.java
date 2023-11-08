package com.apiplanosaude.repository;

import com.apiplanosaude.entity.Beneficiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
    Beneficiario findByCpf(String cpf);

    @Query(value = "select * from beneficiario  where id = :beneficiarioId",
            nativeQuery = true)
    Beneficiario findByBeneficiarioId(Long beneficiarioId);

}
