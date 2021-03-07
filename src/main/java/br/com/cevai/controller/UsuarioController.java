package br.com.cevai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cevai.exceptions.ResourceNotFoundException;
import br.com.cevai.model.Usuario;
import br.com.cevai.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//get All Usuarios
	
	@GetMapping
	public List<Usuario> getAllUsuarios() {
		return this.usuarioRepository.findAll();
	}
	
	//get UsuarioById
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario> getUsuarioById(@PathVariable(value = "id") Long usuarioId)
		throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um usuario com id " + usuarioId));
		return ResponseEntity.ok().body(usuario);
	}
	
	// Cria Usuario
	
	@PostMapping
	public Usuario createUsuario(@RequestBody Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	// Update Usuario
	
	@PutMapping("{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable(value = "id") Long usuarioId,
			@Valid @RequestBody Usuario usuarioUpd) throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um usuario com o id " + usuarioId));
		usuario.setApelido(usuarioUpd.getApelido());
		usuario.setCriados(usuario.getCriados());
		usuario.setPassword(usuarioUpd.getPassword());
		usuario.setSexo(usuarioUpd.getSexo());
		usuario.setUsername(usuarioUpd.getUsername());
		return ResponseEntity.ok(this.usuarioRepository.save(usuario));
	}
 	
	// Delete Local
	public Map<String, Boolean> deleteUsuario(@PathVariable(value = "id") Long usuarioId)
		throws ResourceNotFoundException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado um usuario com o id " + usuarioId));
		this.usuarioRepository.delete(usuario);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/usuarioLastId")
	public int lastInsertedId() {
		int ultimo = usuarioRepository.lastIdInserted();
		return ultimo;
	}
	
}
