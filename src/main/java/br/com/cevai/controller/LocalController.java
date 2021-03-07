package br.com.cevai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cevai.exceptions.ResourceNotFoundException;
import br.com.cevai.model.Local;
import br.com.cevai.repository.LocalRepository;

@RestController
@RequestMapping("/api/local")
public class LocalController {
	
	@Autowired
	private LocalRepository localRepository;
	
	// getAllLocais
	
	@GetMapping
	public List<Local> getAllLocais() {
		return this.localRepository.findAll();
	}
	
	// getLocalById
	
	@GetMapping("{id}")
	public ResponseEntity<Local> getLocalById(@PathVariable(value = "id") Long localId)
		throws ResourceNotFoundException {
		Local local = localRepository.findById(localId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um local com o id " + localId));
		return ResponseEntity.ok().body(local);
	}
	
	// Criacao Local
	
	@PostMapping
	public Local createLocal(@RequestBody Local local) {
		return this.localRepository.save(local);
	}
	
	// Update Local
	
	@PutMapping("{id}")
	public ResponseEntity<Local> updateLocal(@PathVariable(value = "id") Long localId,
			@Valid @RequestBody Local localUpd) throws ResourceNotFoundException {
		Local local = localRepository.findById(localId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um local com o id " + localId));
		local.setBairro(localUpd.getBairro());
		local.setCidade(localUpd.getCidade());
		local.setEstado(localUpd.getEstado());
		local.setLogradouro(localUpd.getLogradouro());
		local.setNome(localUpd.getNome());
		return ResponseEntity.ok(this.localRepository.save(local));
	}
	
	// Delete Local
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteLocal(@PathVariable(value = "id") Long localId)
		throws ResourceNotFoundException {
		Local local = localRepository.findById(localId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um local com o id " + localId));
		this.localRepository.delete(local);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/localLastId")
	public int lastInsertedId() {
		int ultimo = localRepository.lastIdInserted();
		return ultimo;
	}

}
