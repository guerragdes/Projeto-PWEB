package com.example.projetopweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetopweb.model.entity.Usuario;
import com.example.projetopweb.model.repository.UsuarioRepository;

@Repository
@Transactional
public class UsuarioDetailsConfig implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.usuario(login);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + login);
        }

        return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
    }
    
}
