package com.mitocode.dto;

import javax.validation.constraints.NotNull;

public class MedicoDTO {

	private Integer idMedico;
	@NotNull
	private String nombres;
	@NotNull
	private String apellidos;
	@NotNull
	private String cmp;
	@NotNull
	private String fotoUrl;

	public Integer getIdMedico() {
		return idMedico;
	}

	public void setId(Integer idMedico) {
		this.idMedico = idMedico;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCmp() {
		return cmp;
	}

	public void setCmp(String cmp) {
		this.cmp = cmp;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

}
