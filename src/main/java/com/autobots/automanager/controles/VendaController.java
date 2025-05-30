package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

  @Autowired
  private VendaRepository vendaRepository;

  @GetMapping
  public List<Venda> listarVendas() {
    return vendaRepository.findAll();
  }

  @PostMapping
  public Venda criar(@RequestBody Venda venda) {
    return vendaRepository.save(venda);
  }

  @GetMapping("/{id}")
  public Venda obterVenda(@PathVariable Long id) {
    return vendaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
  }

  @PutMapping("/{id}")
  public Venda atualizar(@PathVariable Long id, @RequestBody Venda novaVenda) {
    Venda venda = vendaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

    venda.setData(novaVenda.getData());
    venda.setValorTotal(novaVenda.getValorTotal());
    venda.setMercadorias(novaVenda.getMercadorias());
    venda.setVeiculos(novaVenda.getVeiculos());

    return vendaRepository.save(venda);
  }

  @DeleteMapping("/{id}")
  public void deletar(@PathVariable Long id) {
    vendaRepository.deleteById(id);
  }
}
