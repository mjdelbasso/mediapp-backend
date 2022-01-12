package com.mitocode.dto;

import java.util.List;

public class UsuarioDTO {

	private Integer idUsuario;
	private String username;
	private String password;
	private boolean enabled;
	private List<RolDTO> roles;
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<RolDTO> getRoles() {
		return roles;
	}
	public void setRoles(List<RolDTO> roles) {
		this.roles = roles;
		
	}

	
}
