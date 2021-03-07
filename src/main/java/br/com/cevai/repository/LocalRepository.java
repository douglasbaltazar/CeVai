package br.com.cevai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cevai.model.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long>{
	@Query(value = "SELECT MAX(id) FROM locais", nativeQuery=true)
    int lastIdInserted();

}
