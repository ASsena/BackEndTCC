package com.projeto.tcc.service;

import com.projeto.tcc.dto.UserDetalhesDTO;
import com.projeto.tcc.dto.UsuarioDTO;
import com.projeto.tcc.entities.Role;
import com.projeto.tcc.entities.Usuario;
import com.projeto.tcc.repository.RoleRepository;
import com.projeto.tcc.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = encoder;
    }

    public void adicionarUser(UsuarioDTO usuario) {
        var roles = roleRepository.findByName(Role.Values.BASIC.name());

        var user = usuarioRepository.findByEmail(usuario.email());

        if(user.isEmpty()){
            var newUsuario = new Usuario();
            newUsuario.setEmail(usuario.email());
            newUsuario.setUsername(usuario.username());
            newUsuario.setRoles(Set.of(roles));
            newUsuario.setSenha(passwordEncoder.encode(usuario.senha()));
            usuarioRepository.save(newUsuario);
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
    }


    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<UserDetalhesDTO> listar() {
        return usuarioRepository.findAll().stream().map(user -> new UserDetalhesDTO(user.getEmail(), user.getUsername(), user.getRoles())).toList();

    }

    public Usuario atualizar(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscarPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setSenha(usuario.getSenha());
            usuarioExistente.setEmail(usuario.getEmail());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
