package com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends RepresentationModel<Usuario> {
  @Id
  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Column(nullable = false)
  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Column
  @Schema(description = "Nome social do usuário", example = "João")
  private String nomeSocial;

  @Enumerated(EnumType.STRING)
  @Schema(description = "Perfil do usuário", example = "FUNCIONARIO")
  private PerfilUsuario perfil;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de documentos do usuário")
  private Set<Documento> documentos = new HashSet<>();

  @OneToOne(optional = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "endereco_id", nullable = true)
  private Endereco endereco;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de telefones do usuário")
  private Set<Telefone> telefones = new HashSet<>();

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de emails do usuário")
  private Set<Email> emails = new HashSet<>();

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Credencial de usuário e senha")
  private CredencialUsuarioSenha credencialUsuarioSenha;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Schema(description = "Credencial de código de barras")
  private CredencialCodigoBarra credencialCodigoBarra;

  @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
  @JoinColumn(name = "cliente_id")
  @JsonIgnore
  @Schema(description = "Lista de mercadorias do usuário")
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de veículos do usuário")
  private Set<Veiculo> veiculos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  @JoinColumn(name = "cliente_id")
  @Schema(description = "Lista de vendas do usuário")
  private Set<Venda> vendas = new HashSet<>();
}
