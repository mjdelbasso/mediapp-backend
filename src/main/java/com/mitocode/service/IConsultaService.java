package com.mitocode.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mitocode.dto.ConsultaResumenDTO;
import com.mitocode.model.Consulta;
import com.mitocode.model.Examen;

public interface IConsultaService extends ICRUD<Consulta, Integer>{

	Consulta registrarTransaccional(Consulta consulta, List<Examen> examenes) throws Exception;

	List<Consulta> buscar(String dni, String nombreCompleto);

	List<Consulta> buscarFecha(LocalDateTime fecha1, LocalDateTime fecha2);
	
	List<ConsultaResumenDTO> listarResumen();
	
	byte[] generarReporte();
}
