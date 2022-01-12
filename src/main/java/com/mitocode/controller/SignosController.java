package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.mitocode.dto.SignosDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Signos;
import com.mitocode.service.ISignosService;

@RestController
@RequestMapping("/signos")
public class SignosController {
	
	@Autowired
	private ISignosService service;
	
	@Autowired
	private ModelMapper mapper;

	//@PreAuthorize("@authServiceImpl.tieneAcceso('listar')")
	//@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	@GetMapping
	public ResponseEntity<List<SignosDTO>> listar() throws Exception{
		List<SignosDTO> lista = service.listar().stream().map(p -> mapper.map(p, SignosDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	//@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SignosDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		SignosDTO dtoResponse;
		Signos obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}else {
			dtoResponse = mapper.map(obj, SignosDTO.class);
		}
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<SignosDTO> registrar(@Valid @RequestBody SignosDTO dtoRequest) throws Exception {
		Signos p = mapper.map(dtoRequest, Signos.class);
		Signos obj = service.registrar(p);
		SignosDTO dtoResponse = mapper.map(obj, SignosDTO.class);
		return new ResponseEntity<>(dtoResponse, HttpStatus.CREATED);
	}*/
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody SignosDTO dtoRequest) throws Exception {
		Signos p = mapper.map(dtoRequest, Signos.class);
		Signos obj = service.registrar(p);
		SignosDTO dtoResponse = mapper.map(obj, SignosDTO.class);
		//localhost:8080/Signos/9
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dtoResponse.getIdSignos()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<SignosDTO> modificar(@Valid @RequestBody SignosDTO dtoRequest) throws Exception {
		Signos pac = service.listarPorId(dtoRequest.getIdSignos());
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + dtoRequest.getIdSignos());	
		}		
		
		Signos p = mapper.map(dtoRequest, Signos.class);		 
		Signos obj = service.modificar(p);		
		SignosDTO dtoResponse = mapper.map(obj, SignosDTO.class);
		
		return new ResponseEntity<>(dtoResponse, HttpStatus.OK);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Signos pac = service.listarPorId(id);
		
		if(pac == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		service.eliminar(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<SignosDTO> listarHateoas (@PathVariable("id") Integer id) throws Exception{
		Signos obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		
		SignosDTO dto = mapper.map(obj, SignosDTO.class);
		
		EntityModel<SignosDTO> recurso = EntityModel.of(dto);
		//localhost:8080/Signos/1
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		
		recurso.add(link1.withRel("signos-link"));
		return recurso;
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<SignosDTO>> listarPageable(Pageable page) throws Exception{
		Page<SignosDTO> pacientes = service.listarPageable(page).map(p -> mapper.map(p, SignosDTO.class));
		
		return new ResponseEntity<>(pacientes, HttpStatus.OK);
	}
	
}