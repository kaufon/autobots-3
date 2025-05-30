package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioController;
import com.autobots.automanager.entidades.Usuario;

@Component
public class AdicionarLinkUsuarioServico implements AdicionarLinkServico<Usuario> {

  @Override
  public void adicionarLink(List<Usuario> clientes) {
    for (Usuario cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(UsuarioController.class).obterUsuario(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Usuario cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioController.class)
            .listarUsuarios())
        .withRel("usuarios");
    cliente.add(linkProprio);
  }
}
