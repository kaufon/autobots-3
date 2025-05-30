package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.PecaController;
import com.autobots.automanager.entidades.Peca;

@Component
public class AdicionarLinkPecaServico implements AdicionarLinkServico<Peca> {

  @Override
  public void adicionarLink(List<Peca> clientes) {
    for (Peca cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(PecaController.class).obterPeca(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Peca cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(PecaController.class)
            .listarPecas())
        .withRel("pecas");
    cliente.add(linkProprio);
  }
}
