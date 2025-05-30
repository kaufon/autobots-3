package com.autobots.automanager;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class AutomanagerApplication {
  public static void main(String[] args) {
    SpringApplication.run(AutomanagerApplication.class, args);
  }

  @Bean
  CommandLineRunner initData(
      EmpresaRepository empresaRepository,
      UsuarioRepository usuarioRepository,
      VendaRepository vendaRepository,
      VeiculoRepository veiculoRepository,
      PecaRepository pecaRepository,
      MercadoriaRepository mercadoriaRepository) {
    return args -> {
      Empresa empresa = new Empresa();
      empresa.setNome("Auto Peças Ltda");
      empresa.setCnpj("12.345.678/0001-90");
      empresa.setEndereco("Rua das Oficinas, 123");
      empresa.setTelefone("(11) 98765-4321");
      empresaRepository.save(empresa);

      Usuario usuario = new Usuario();
      usuario.setNome("João Silva");
      usuario.setEmail("joao.silva@email.com");
      usuario.setSenha("senha123");
      usuario.setTipo(com.autobots.automanager.enumeracoes.TipoUsuario.FUNCIONARIO);
      usuario.setEmpresa(empresa);
      usuarioRepository.save(usuario);

      Peca peca1 = new Peca();
      peca1.setNome("Filtro de óleo");
      peca1.setQuantidadeEstoque(50);
      peca1.setPrecoUnitario(30.0);
      pecaRepository.save(peca1);

      Peca peca2 = new Peca();
      peca2.setNome("Velas de ignição");
      peca2.setQuantidadeEstoque(100);
      peca2.setPrecoUnitario(15.0);
      pecaRepository.save(peca2);

      Mercadoria mercadoria = new Mercadoria();
      mercadoria.setQuantidade(4);
      mercadoria.setPrecoUnitario(30.0);
      mercadoria.setPeca(peca1);

      Veiculo veiculo = new Veiculo();
      veiculo.setPlaca("ABC-1234");
      veiculo.setModelo("Gol");
      veiculo.setMarca("Volkswagen");
      veiculo.setCor("Azul");
      veiculo.setAnoFabricacao(LocalDate.of(2018, 6, 15));

      Venda venda = new Venda();
      venda.setData(new Date());
      venda.setValorTotal(120.0);
      venda.setMercadorias(Arrays.asList(mercadoria));
      venda.setVeiculos(Arrays.asList(veiculo));

      mercadoria.setVenda(venda);
      veiculo.setVenda(venda);

      vendaRepository.save(venda);
    };
  }
}
