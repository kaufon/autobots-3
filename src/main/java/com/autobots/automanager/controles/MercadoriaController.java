package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Peca;
import com.autobots.automanager.repositorios.MercadoriaRepository;
import com.autobots.automanager.repositorios.PecaRepository;
import com.autobots.automanager.servicos.AdicionarLinkMercadoriaServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {

  @Autowired
  private MercadoriaRepository mercadoriaRepository;

  @Autowired
  private PecaRepository pecaRepository;

  @Autowired
  private AdicionarLinkMercadoriaServico adicionarLink;

  @GetMapping("/listar")
  public ResponseEntity<List<Mercadoria>> listarMercadorias() {
    List<Mercadoria> mercadorias = mercadoriaRepository.findAll();
    if (mercadorias.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(mercadorias);
    return ResponseEntity.ok(mercadorias);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long id) {
    Optional<Mercadoria> mercadoria = mercadoriaRepository.findById(id);
    if (mercadoria.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(mercadoria.get());
    return ResponseEntity.ok(mercadoria.get());
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Mercadoria> criar(@RequestBody Mercadoria mercadoria) {
    if (mercadoria.getPeca() == null || mercadoria.getPeca().getId() == null) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Peca> peca = pecaRepository.findById(mercadoria.getPeca().getId());
    if (peca.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    mercadoria.setPeca(peca.get());
    Mercadoria nova = mercadoriaRepository.save(mercadoria);
    adicionarLink.adicionarLink(nova);
    return ResponseEntity.ok(nova);
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Mercadoria> atualizar(@PathVariable Long id, @RequestBody Mercadoria novaMercadoria) {
    Optional<Mercadoria> mercadoriaOptional = mercadoriaRepository.findById(id);
    if (mercadoriaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Mercadoria mercadoria = mercadoriaOptional.get();

    if (novaMercadoria.getPeca() != null && novaMercadoria.getPeca().getId() != null) {
      Optional<Peca> peca = pecaRepository.findById(novaMercadoria.getPeca().getId());
      if (peca.isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      mercadoria.setPeca(peca.get());
    }

    mercadoria.setQuantidade(novaMercadoria.getQuantidade());
    mercadoria.setPrecoUnitario(novaMercadoria.getPrecoUnitario());

    Mercadoria atualizada = mercadoriaRepository.save(mercadoria);
    adicionarLink.adicionarLink(atualizada);
    return ResponseEntity.ok(atualizada);
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    Optional<Mercadoria> mercadoria = mercadoriaRepository.findById(id);
    if (mercadoria.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    mercadoriaRepository.delete(mercadoria.get());
    return ResponseEntity.noContent().build();
  }
}
