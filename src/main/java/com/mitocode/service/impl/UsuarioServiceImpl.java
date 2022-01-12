package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mitocode.model.Usuario;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.service.IUsuarioService;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Usuario, Integer> implements UserDetailsService, IUsuarioService{

	@Autowired
	private IUsuarioRepo repo;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = repo.findOneByUsername(username);
		
		if(usuario == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		usuario.getRoles().forEach(rol -> {
			roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		});
				
		UserDetails ud = new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true, roles);
		
		return ud;
	}

	@Override
	protected IGenericRepo<Usuario, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

}
