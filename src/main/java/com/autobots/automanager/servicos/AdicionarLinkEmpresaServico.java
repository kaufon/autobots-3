package com.autobots.automanager.servicos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaController;
import com.autobots.automanager.entidades.Empresa;

@Component
public class AdicionarLinkEmpresaServico implements AdicionarLinkServico<Empresa> {

  @Override
  public void adicionarLink(List<Empresa> clientes) {
    for (Empresa cliente : clientes) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder.methodOn(EmpresaController.class).obterEmpresa(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Empresa cliente) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaController.class)
            .listarEmpresas())
        .withRel("empresas");
    cliente.add(linkProprio);
  }
}
