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
import br.com.cevai.model.Atracao;
import br.com.cevai.repository.AtracaoRepository;

@RestController
@RequestMapping("/api/atracao")
public class AtracaoController {
	
	@Autowired
	private AtracaoRepository atracaoRepository;

	//get All Atracoes
	
	@GetMapping
	public List<Atracao> getAllAtracoes() {
		return this.atracaoRepository.findAll();
	}
	
	// get Atracao By Id
	
	@GetMapping("{id}")
	public ResponseEntity<Atracao> getAtracaoById(@PathVariable(value = "id") Long atracaoId) throws ResourceNotFoundException {
		Atracao atracao = atracaoRepository.findById(atracaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado uma atração com id " + atracaoId));
		return ResponseEntity.ok().body(atracao);
	}
	
	// Criar Atracao
	
	@PostMapping
	public Atracao createAtracao(@RequestBody Atracao atracao) {
		return this.atracaoRepository.save(atracao);
	}
	
	// Update Atracao
	
	@PutMapping("{id}")
	public ResponseEntity<Atracao> updateAtracao(@PathVariable(value = "id") Long atracaoId, 
			@Valid @RequestBody Atracao atracaoUpd) throws ResourceNotFoundException {
		Atracao atracao = atracaoRepository.findById(atracaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada uma atração com id " + atracaoId));
		atracao.setNomeAtracao(atracaoUpd.getNomeAtracao());
		atracao.setGenero(atracaoUpd.getGenero());
		return ResponseEntity.ok(this.atracaoRepository.save(atracao));
		
	}
	
	// Delete Atracao
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteAtracao(@PathVariable(value = "id") Long atracaoId) throws ResourceNotFoundException {
		Atracao atracao = atracaoRepository.findById(atracaoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrada uma atração com id " + atracaoId));
		this.atracaoRepository.delete(atracao);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/atracaoLastId/")
	public int lastInsertedId() {
		int ultimo = atracaoRepository.lastIdInserted();
		return ultimo;
	}

}
