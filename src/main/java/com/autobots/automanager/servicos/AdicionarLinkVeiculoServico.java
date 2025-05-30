package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoController;
import com.autobots.automanager.entidades.Veiculo;

@Component
public class AdicionarLinkVeiculoServico implements AdicionarLinkServico<Veiculo> {

  @Override
  public void adicionarLink(List<Veiculo> clientes) {
    for (Veiculo cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(VeiculoController.class).obterVeiculo(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Veiculo cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(VeiculoController.class)
            .listarVeiculos())
        .withRel("veiculos");
    cliente.add(linkProprio);
  }
}
