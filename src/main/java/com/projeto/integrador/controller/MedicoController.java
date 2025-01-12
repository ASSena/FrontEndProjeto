package com.projeto.integrador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.integrador.dto.MedicoEditar;
import com.projeto.integrador.dto.MedicoReceber;
import com.projeto.integrador.service.MedicoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
@CrossOrigin
public class MedicoController {
	
	@Autowired
	MedicoService service;

	@CrossOrigin
	@PostMapping("cadastrarmedicos")
	@Transactional
	public ResponseEntity<MedicoReceber> cadastrar(@RequestBody @Valid MedicoReceber receber){
		service.registrarMedico(receber);		
		return ResponseEntity.ok().build();
	}
	
	@CrossOrigin
	@GetMapping
	@Transactional
	public ResponseEntity<?> listar(){
		var medico = service.detalharTodosMedicos();
		return ResponseEntity.ok(medico);
	}
	
	@CrossOrigin
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletarMedico(@PathVariable Long id){		
		try {service.deletarMedico(id);
			return ResponseEntity.noContent().build();
		}catch(EntityNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                .body("Médico não encontrado");
		}
	}
	
	@CrossOrigin
	@PutMapping("editarmedico{id}")
	public ResponseEntity<?> editarMedico(@PathVariable Long id, @RequestBody MedicoEditar editar){
		try {
			service.editarMedico(id, editar);
			return ResponseEntity.ok(service.detalharMedico(id));	
		}catch(EntityNotFoundException e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND) 
		                .body("Médico não encontrado");
		}
		
	}
	
	@CrossOrigin
	@GetMapping("{id}")
	public ResponseEntity<?> medicoEspecifico(@PathVariable Long id){
		return ResponseEntity.ok(service.medicoId(id));
	}
}