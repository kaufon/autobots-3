package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.repositorios.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @PostMapping
    public Veiculo criarVeiculo(@RequestBody Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    @GetMapping
    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Veiculo> obterVeiculo(@PathVariable Long id) {
        return veiculoRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deletarVeiculo(@PathVariable Long id) {
        veiculoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Veiculo atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo dadosAtualizados) {
        return veiculoRepository.findById(id).map(veiculo -> {
            veiculo.setPlaca(dadosAtualizados.getPlaca());
            veiculo.setModelo(dadosAtualizados.getModelo());
            veiculo.setMarca(dadosAtualizados.getMarca());
            veiculo.setCor(dadosAtualizados.getCor());
            veiculo.setAnoFabricacao(dadosAtualizados.getAnoFabricacao());
            return veiculoRepository.save(veiculo);
        }).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }
}
