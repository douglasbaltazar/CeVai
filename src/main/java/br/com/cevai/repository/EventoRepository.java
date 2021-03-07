package br.com.cevai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cevai.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>{
	@Query(value = "SELECT MAX(id) FROM eventos", nativeQuery=true)
    int lastIdInserted();
}
