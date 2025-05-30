package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Peca;
import com.autobots.automanager.repositorios.PecaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pecas")
public class PecaController {

  @Autowired
  private PecaRepository pecaRepository;

  @GetMapping
  public List<Peca> listarPecas() {
    return pecaRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Peca> obterPeca(@PathVariable Long id) {
    return pecaRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Peca cadastrarPeca(@RequestBody Peca peca) {
    return pecaRepository.save(peca);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Peca> atualizarPeca(@PathVariable Long id, @RequestBody Peca pecaAtualizada) {
    return pecaRepository.findById(id)
        .map(peca -> {
          peca.setNome(pecaAtualizada.getNome());
          peca.setQuantidadeEstoque(pecaAtualizada.getQuantidadeEstoque());
          peca.setPrecoUnitario(pecaAtualizada.getPrecoUnitario());
          pecaRepository.save(peca);
          return ResponseEntity.ok(peca);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deletarPeca(@PathVariable Long id) {
    return pecaRepository.findById(id)
        .map(peca -> {
          pecaRepository.delete(peca);
          return ResponseEntity.<Void>noContent().build();
        })
        .orElse(ResponseEntity.notFound().build());
  }

}
