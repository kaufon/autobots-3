package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepository;
import com.autobots.automanager.servicos.AdicionarLinkUsuarioServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private AdicionarLinkUsuarioServico adicionarLink;

  @GetMapping("/listar")
  public ResponseEntity<List<Usuario>> listarUsuarios() {
    List<Usuario> usuarios = usuarioRepository.findAll();
    if (usuarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(usuarios);
    return ResponseEntity.ok(usuarios);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> obterUsuario(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    if (usuario.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(usuario.get());
    return ResponseEntity.ok(usuario.get());
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
    Usuario novoUsuario = usuarioRepository.save(usuario);
    adicionarLink.adicionarLink(novoUsuario);
    return ResponseEntity.ok(novoUsuario);
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
    Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
    if (usuarioOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Usuario usuario = usuarioOptional.get();
    usuario.setNome(usuarioAtualizado.getNome());
    usuario.setEmail(usuarioAtualizado.getEmail());
    usuario.setSenha(usuarioAtualizado.getSenha());
    usuario.setTipo(usuarioAtualizado.getTipo());
    usuario.setEmpresa(usuarioAtualizado.getEmpresa());

    Usuario usuarioSalvo = usuarioRepository.save(usuario);
    adicionarLink.adicionarLink(usuarioSalvo);
    return ResponseEntity.ok(usuarioSalvo);
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    if (usuario.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    usuarioRepository.delete(usuario.get());
    return ResponseEntity.noContent().build();
  }
}
