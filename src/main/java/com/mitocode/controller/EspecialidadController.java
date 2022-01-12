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

import com.mitocode.dto.EspecialidadDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Especialidad;
import com.mitocode.service.IEspecialidadService;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {
	
	@Autowired
	private IEspecialidadService service;
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping
	public ResponseEntity<List<EspecialidadDTO>> listar() throws Exception{
		List<EspecialidadDTO> lista = service.listar().stream().map(p -> mapper.map(p, EspecialidadDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	//@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<EspecialidadDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		EspecialidadDTO dtoResponse;
		Especialidad obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, EspecialidadDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<EspecialidadDTO> registrar(@Valid @RequestBody EspecialidadDTO dtoRequest) throws Exception {
		Especialidad p = mapper.map(dtoRequest, Especialidad.class);
		Especialidad obj = service.registrar(p);
		EspecialidadDTO dtoResponse = mapper.map(obj, EspecialidadDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}*/
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody EspecialidadDTO dtoRequest) throws Exception {
		Especialidad p = mapper.map(dtoRequest, Especialidad.class);
		Especialidad obj = service.registrar(p);
		EspecialidadDTO dtoResponse = mapper.map(obj, EspecialidadDTO.class);
		//localhost:8080/Especialidads/9
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdEspecialidad()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<EspecialidadDTO> modificar(@Valid @RequestBody EspecialidadDTO dtoRequest) throws Exception {
		Especialidad pac = service.listarPorId(dtoRequest.getIdEspecialidad());
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdEspecialidad());	
		}		
		
		Especialidad p = mapper.map(dtoRequest, Especialidad.class);		 
		Especialidad obj = service.modificar(p);		
		EspecialidadDTO dtoResponse = mapper.map(obj, EspecialidadDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Especialidad pac = service.listarPorId(id);
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<EspecialidadDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
		Especialidad obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		EspecialidadDTO dto = mapper.map(obj, EspecialidadDTO.class);
		
		EntityModel<EspecialidadDTO> recurso = EntityModel.of(dto);
		//localhost:8080/Especialidads/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		
		recurso.add(link1.withRel("especialidad-link"));
		return recurso;
	}
	
}
