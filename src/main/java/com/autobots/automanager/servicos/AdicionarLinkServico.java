package com.autobots.automanager.servicos;

import java.util.List;

public interface AdicionarLinkServico<T> {
  public void adicionarLink(List<T> lista);

  public void adicionarLink(T objeto);
}
