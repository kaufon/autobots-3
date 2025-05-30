package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @PostMapping
    public Servico criarServico(@RequestBody Servico servico) {
        return servicoRepository.save(servico);
    }

    @GetMapping
    public List<Servico> listarServicos() {
        return servicoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Servico> obterServico(@PathVariable Long id) {
        return servicoRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Servico atualizarServico(@PathVariable Long id, @RequestBody Servico dadosAtualizados) {
        return servicoRepository.findById(id).map(servico -> {
            servico.setNome(dadosAtualizados.getNome());
            servico.setDescricao(dadosAtualizados.getDescricao());
            servico.setPreco(dadosAtualizados.getPreco());
            return servicoRepository.save(servico);
        }).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletarServico(@PathVariable Long id) {
        servicoRepository.deleteById(id);
    }
}
