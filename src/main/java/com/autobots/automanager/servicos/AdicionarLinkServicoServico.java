package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ServicoController;
import com.autobots.automanager.entidades.Servico;

@Component
public class AdicionarLinkServicoServico implements AdicionarLinkServico<Servico> {

  @Override
  public void adicionarLink(List<Servico> clientes) {
    for (Servico cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(ServicoController.class).obterServico(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Servico cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ServicoController.class)
            .listarServicos())
        .withRel("servicos");
    cliente.add(linkProprio);
  }
}
