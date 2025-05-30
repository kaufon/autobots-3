package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Peca;
import com.autobots.automanager.repositorios.MercadoriaRepository;
import com.autobots.automanager.repositorios.PecaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaController {

  @Autowired
  private MercadoriaRepository mercadoriaRepository;

  @Autowired
  private PecaRepository pecaRepository;

  @GetMapping
  public List<Mercadoria> listarMercadorias() {
    return mercadoriaRepository.findAll();
  }

  @PostMapping
  public Mercadoria criar(@RequestBody Mercadoria mercadoria) {
    if (mercadoria.getPeca() != null && mercadoria.getPeca().getId() != null) {
      Peca peca = pecaRepository.findById(mercadoria.getPeca().getId())
          .orElseThrow(() -> new RuntimeException("Peça não encontrada"));
      mercadoria.setPeca(peca);
    } else {
      throw new RuntimeException("Peça é obrigatória");
    }

    return mercadoriaRepository.save(mercadoria);
  }

  @GetMapping("/{id}")
  public Mercadoria obterMercadoria(@PathVariable Long id) {
    return mercadoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
  }

  @PutMapping("/{id}")
  public Mercadoria atualizar(@PathVariable Long id, @RequestBody Mercadoria novaMercadoria) {
    Mercadoria mercadoria = mercadoriaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));

    if (novaMercadoria.getPeca() != null && novaMercadoria.getPeca().getId() != null) {
      Peca peca = pecaRepository.findById(novaMercadoria.getPeca().getId())
          .orElseThrow(() -> new RuntimeException("Peça não encontrada"));
      mercadoria.setPeca(peca);
    }

    mercadoria.setQuantidade(novaMercadoria.getQuantidade());
    mercadoria.setPrecoUnitario(novaMercadoria.getPrecoUnitario());

    return mercadoriaRepository.save(mercadoria);
  }

  @DeleteMapping("/{id}")
  public void deletar(@PathVariable Long id) {
    mercadoriaRepository.deleteById(id);
  }
}
