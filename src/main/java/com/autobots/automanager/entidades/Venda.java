package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = { "cliente", "funcionario", "veiculo" })
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Venda extends RepresentationModel<Venda> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date cadastro;

  @Column(nullable = false, unique = true)
  private String identificacao;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Usuario cliente;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Usuario funcionario;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Set<Servico> servicos = new HashSet<>();

  @JsonIgnore
  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Veiculo veiculo;

  @JsonProperty("cliente_id") // Define o nome do campo no JSON
  public Long getClienteId() {
    return (cliente != null) ? cliente.getId() : null;
  }

  @JsonProperty("funcionario_id")
  public Long getFuncionarioId() {
    return (funcionario != null) ? funcionario.getId() : null;
  }

  @JsonProperty("veiculo_id")
  public Long getVeiculoId() {
    return (veiculo != null) ? veiculo.getId() : null;
  }

  @JsonProperty("mercadorias_ids")
  public Set<Long> getMercadoriasIds() {
    if (mercadorias == null || mercadorias.isEmpty()) {
      return null;
    }
    return mercadorias.stream().map(Mercadoria::getId).collect(Collectors.toSet());
  }

  @JsonProperty("servicos_ids")
  public Set<Long> getServicosIds() {
    if (servicos == null || servicos.isEmpty()) {
      return null;
    }
    return servicos.stream().map(Servico::getId).collect(Collectors.toSet());
  }
}
