package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepository;
import com.autobots.automanager.servicos.AdicionarLinkEmpresaServico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

  @Autowired
  private EmpresaRepository empresaRepository;

  @Autowired
  private AdicionarLinkEmpresaServico adicionarLink;

  @GetMapping("/empresas")
  public ResponseEntity<List<Empresa>> listarEmpresas() {
    List<Empresa> empresas = empresaRepository.findAll();
    if (empresas.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    adicionarLink.adicionarLink(empresas);
    return ResponseEntity.ok(empresas);
  }

  @GetMapping("/empresa/{id}")
  public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
    Optional<Empresa> empresa = empresaRepository.findById(id);
    if (empresa.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    adicionarLink.adicionarLink(empresa.get());
    return ResponseEntity.ok(empresa.get());
  }

  @PostMapping("/cadastro")
  public ResponseEntity<Empresa> criarEmpresa(@RequestBody Empresa empresa) {
    Empresa novaEmpresa = empresaRepository.save(empresa);
    adicionarLink.adicionarLink(novaEmpresa);
    return ResponseEntity.ok(novaEmpresa);
  }

  @DeleteMapping("/deletar/{id}")
  public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
    Optional<Empresa> empresa = empresaRepository.findById(id);
    if (empresa.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    empresaRepository.delete(empresa.get());
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/atualizar/{id}")
  public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
    Optional<Empresa> empresaOptional = empresaRepository.findById(id);
    if (empresaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Empresa empresaExistente = empresaOptional.get();

    empresaExistente.setNome(empresaAtualizada.getNome());
    empresaExistente.setNome(empresaAtualizada.getNome());
    empresaExistente.setTelefone(empresaAtualizada.getTelefone());
    empresaExistente.setEndereco(empresaAtualizada.getEndereco());
    empresaExistente.setCnpj(empresaAtualizada.getCnpj());
    Empresa empresaSalva = empresaRepository.save(empresaExistente);
    adicionarLink.adicionarLink(empresaSalva);
    return ResponseEntity.ok(empresaSalva);
  }
}
