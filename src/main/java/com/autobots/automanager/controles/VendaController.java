package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.VendaRepository;
import com.autobots.automanager.servicos.AdicionarLinkVendaServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/venda")
public class VendaController {

  @Autowired
  private VendaRepository vendaRepository;

  @Autowired
  private AdicionarLinkVendaServico linkService;

  @GetMapping("/listar")
  public ResponseEntity<List<Venda>> listarVendas() {
    List<Venda> vendas = vendaRepository.findAll();

    if (vendas.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    vendas.forEach(linkService::adicionarLink);
    return ResponseEntity.ok(vendas);
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Venda> criar(@RequestBody Venda venda) {
    Venda novaVenda = vendaRepository.save(venda);
    linkService.adicionarLink(novaVenda);
    return ResponseEntity.ok(novaVenda);
  }

  @GetMapping("/obter/{id}")
  public ResponseEntity<Venda> obterVenda(@PathVariable Long id) {
    return vendaRepository.findById(id)
        .map(venda -> {
          linkService.adicionarLink(venda);
          return ResponseEntity.ok(venda);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Venda> atualizar(@PathVariable Long id, @RequestBody Venda novaVenda) {
    return vendaRepository.findById(id)
        .map(venda -> {
          venda.setData(novaVenda.getData());
          venda.setValorTotal(novaVenda.getValorTotal());
          venda.setMercadorias(novaVenda.getMercadorias());
          venda.setVeiculos(novaVenda.getVeiculos());

          Venda vendaAtualizada = vendaRepository.save(venda);
          linkService.adicionarLink(vendaAtualizada);
          return ResponseEntity.ok(vendaAtualizada);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    Optional<Venda> vendaOptional = vendaRepository.findById(id);
    if (vendaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    vendaRepository.delete(vendaOptional.get());
    return ResponseEntity.noContent().build();
  }
}
