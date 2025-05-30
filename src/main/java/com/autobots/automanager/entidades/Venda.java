package com.autobots.automanager.entidades;

import javax.persistence.*;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vendas")
public class Venda extends RepresentationModel<Venda> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date data;

  private Double valorTotal;

  @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
  private List<Mercadoria> mercadorias;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "venda_id")
  private List<Veiculo> veiculos;

  @PrePersist
  @PreUpdate
  private void validarVenda() {
    boolean semVeiculos = veiculos == null || veiculos.isEmpty();
    boolean semMercadorias = mercadorias == null || mercadorias.isEmpty();

    if (semVeiculos && semMercadorias) {
      throw new IllegalStateException("A venda deve conter pelo menos uma mercadoria ou um veículo.");
    }
  }
}
