package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.ExamenDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Examen;
import com.mitocode.service.IExamenService;

@RestController
@RequestMapping("/examenes")
public class ExamenController {
	
	@Autowired
	private IExamenService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<ExamenDTO>> listar() throws Exception{
		List<ExamenDTO> lista = service.listar().stream().map(p -> mapper.map(p, ExamenDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	//@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ExamenDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		ExamenDTO dtoResponse;
		Examen obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, ExamenDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<ExamenDTO> registrar(@Valid @RequestBody ExamenDTO dtoRequest) throws Exception {
		Examen p = mapper.map(dtoRequest, Examen.class);
		Examen obj = service.registrar(p);
		ExamenDTO dtoResponse = mapper.map(obj, ExamenDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}*/
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody ExamenDTO dtoRequest) throws Exception {
		Examen p = mapper.map(dtoRequest, Examen.class);
		Examen obj = service.registrar(p);
		ExamenDTO dtoResponse = mapper.map(obj, ExamenDTO.class);
		//localhost:8080/Examens/9
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdExamen()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<ExamenDTO> modificar(@Valid @RequestBody ExamenDTO dtoRequest) throws Exception {
		Examen pac = service.listarPorId(dtoRequest.getIdExamen());
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdExamen());	
		}		
		
		Examen p = mapper.map(dtoRequest, Examen.class);		 
		Examen obj = service.modificar(p);		
		ExamenDTO dtoResponse = mapper.map(obj, ExamenDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Examen pac = service.listarPorId(id);
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<ExamenDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
		Examen obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		ExamenDTO dto = mapper.map(obj, ExamenDTO.class);
		
		EntityModel<ExamenDTO> recurso = EntityModel.of(dto);
		//localhost:8080/Examens/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		
		recurso.add(link1.withRel("examen-link"));
		return recurso;
	}
	
}
