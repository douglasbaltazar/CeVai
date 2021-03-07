package br.com.cevai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cevai.model.Atracao;


@Repository
public interface AtracaoRepository extends JpaRepository<Atracao, Long> {
	@Query(value = "SELECT MAX(id) FROM atracoes", nativeQuery=true)
    int lastIdInserted();
}
