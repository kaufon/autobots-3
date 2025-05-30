package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  private final UsuarioRepository usuarioRepository;

  public UsuarioController(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @GetMapping
  public CollectionModel<EntityModel<Usuario>> listarUsuarios() {
    List<EntityModel<Usuario>> usuarios = usuarioRepository.findAll().stream()
        .map(usuario -> EntityModel.of(usuario,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(usuario.getId()))
                .withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios())
                .withRel("usuarios")))
        .collect(Collectors.toList());

    return CollectionModel.of(usuarios,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios()).withSelfRel());
  }

  @GetMapping("/{id}")
  public EntityModel<Usuario> obterUsuario(@PathVariable Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    return EntityModel.of(usuario,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(id)).withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios())
            .withRel("usuarios"));
  }

  @PostMapping
  public ResponseEntity<EntityModel<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
    Usuario novoUsuario = usuarioRepository.save(usuario);

    EntityModel<Usuario> usuarioModel = EntityModel.of(novoUsuario,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(novoUsuario.getId()))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios())
            .withRel("usuarios"));

    return ResponseEntity
        .created(WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(novoUsuario.getId())).toUri())
        .body(usuarioModel);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EntityModel<Usuario>> atualizarUsuario(@PathVariable Long id,
      @RequestBody Usuario usuarioAtualizado) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuario.setNome(usuarioAtualizado.getNome());
    usuario.setEmail(usuarioAtualizado.getEmail());
    usuario.setSenha(usuarioAtualizado.getSenha());
    usuario.setTipo(usuarioAtualizado.getTipo());
    usuario.setEmpresa(usuarioAtualizado.getEmpresa());

    Usuario usuarioSalvo = usuarioRepository.save(usuario);

    EntityModel<Usuario> usuarioModel = EntityModel.of(usuarioSalvo,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(usuarioSalvo.getId()))
            .withSelfRel(),
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).listarUsuarios())
            .withRel("usuarios"));

    return ResponseEntity.ok(usuarioModel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    usuarioRepository.delete(usuario);
    return ResponseEntity.noContent().build();
  }
}
