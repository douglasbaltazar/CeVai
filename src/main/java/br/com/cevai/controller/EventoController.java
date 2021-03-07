package br.com.cevai.controller;

import java.util.ArrayList;
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
import br.com.cevai.model.Evento;
import br.com.cevai.model.Usuario;
import br.com.cevai.repository.EventoRepository;

@RestController
@RequestMapping("/api/evento")
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	//get All Eventos
	
	@GetMapping
	public List<Evento> getAllEventos() {
		return this.eventoRepository.findAll();
	}
	
	// get Evento byId
	
	@GetMapping("{id}")
	public ResponseEntity<Evento> getEventoById(@PathVariable(value = "id") Long eventoId)
			throws ResourceNotFoundException {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um evento com o id " + eventoId));
		return ResponseEntity.ok().body(evento);
	}
	
	// Criacao Evento
	
	@PostMapping
	public Evento createEvento(@RequestBody Evento evento) {
		return this.eventoRepository.save(evento);
	}
	
	// Update Evento
	
	@PutMapping("{id}")
	public ResponseEntity<Evento> updateEvento(@PathVariable(value = "id") Long eventoId,
			@Valid @RequestBody Evento eventoUpd) throws ResourceNotFoundException {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um evento com o id " + eventoId));
		evento.setAtracoes(eventoUpd.getAtracoes());
		evento.setCriador(eventoUpd.getCriador());
		evento.setDataHora(eventoUpd.getDataHora());
		evento.setLocal(eventoUpd.getLocal());
		evento.setNome(eventoUpd.getNome());
		evento.setPreco(eventoUpd.getPreco());
		evento.setParticipantes(new ArrayList<Usuario>());
		return ResponseEntity.ok(this.eventoRepository.save(evento));
	}
	
	
	
	// Delete Evento
	
	@DeleteMapping("{id}")
	public Map<String, Boolean> deleteEvento(@PathVariable(value = "id") Long eventoId)
			throws ResourceNotFoundException {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um evento com o id " + eventoId));
		this.eventoRepository.delete(evento);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/eventoLastId/")
	public int lastInsertedId() {
		int ultimo = eventoRepository.lastIdInserted();
		return ultimo;
	}

}
