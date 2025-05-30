package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaController;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class AdicionarLinkMercadoriaServico implements AdicionarLinkServico<Mercadoria> {

  @Override
  public void adicionarLink(List<Mercadoria> clientes) {
    for (Mercadoria cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(MercadoriaController.class).obterMercadoria(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Mercadoria cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(MercadoriaController.class)
            .listarMercadorias())
        .withRel("mercadorias");
    cliente.add(linkProprio);
  }
}
