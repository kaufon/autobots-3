package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Peca;
import com.autobots.automanager.repositorios.PecaRepository;
import com.autobots.automanager.servicos.AdicionarLinkPecaServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/peca")
public class PecaController {

  @Autowired
  private PecaRepository pecaRepository;

  @Autowired
  private AdicionarLinkPecaServico adicionarLink;

  @GetMapping("/listar")
  public ResponseEntity<List<Peca>> listarPecas() {
    List<Peca> pecas = pecaRepository.findAll();
    if (pecas.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(pecas);
    return ResponseEntity.ok(pecas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Peca> obterPeca(@PathVariable Long id) {
    Optional<Peca> peca = pecaRepository.findById(id);
    if (peca.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(peca.get());
    return ResponseEntity.ok(peca.get());
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Peca> cadastrarPeca(@RequestBody Peca peca) {
    Peca nova = pecaRepository.save(peca);
    adicionarLink.adicionarLink(nova);
    return ResponseEntity.ok(nova);
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
    Optional<Peca> pecaOptional = pecaRepository.findById(id);
    if (pecaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Peca peca = pecaOptional.get();
    peca.setNome(pecaAtualizada.getNome());
    peca.setQuantidadeEstoque(pecaAtualizada.getQuantidadeEstoque());
    peca.setPrecoUnitario(pecaAtualizada.getPrecoUnitario());

    Peca atualizada = pecaRepository.save(peca);
    adicionarLink.adicionarLink(atualizada);
    return ResponseEntity.ok(atualizada);
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
    Optional<Peca> peca = pecaRepository.findById(id);
    if (peca.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    pecaRepository.delete(peca.get());
    return ResponseEntity.noContent().build();
  }
}
