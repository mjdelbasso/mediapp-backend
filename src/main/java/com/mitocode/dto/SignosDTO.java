package com.mitocode.dto;

import java.time.LocalDateTime;

import com.sun.istack.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Signos DTO Data")
public class SignosDTO {


	private Integer idSignos;
	
	@NotNull
	private PacienteDTO paciente;
	
	@NotNull
	private LocalDateTime fecha;
	
	@NotNull
	private String temperatura;
	
	@NotNull
	private String pulso;
	
	@NotNull
	private String ritmo;

	public Integer getIdSignos() {
		return idSignos;
	}

	public void setIdSignos(Integer idSignos) {
		this.idSignos = idSignos;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getPulso() {
		return pulso;
	}

	public void setPulso(String pulso) {
		this.pulso = pulso;
	}

	public String getRitmo() {
		return ritmo;
	}

	public void setRitmo(String ritmo) {
		this.ritmo = ritmo;
	}
	
	
}
