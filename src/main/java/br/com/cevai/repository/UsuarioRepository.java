package br.com.cevai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.cevai.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value = "SELECT MAX(id) FROM usuarios", nativeQuery=true)
    int lastIdInserted();
}
