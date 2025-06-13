package api.entity;

import api.entity.audit.Auditable;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_permissoes")
public class Permissao extends Auditable implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "permissao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioPermissao> usuariosPermissoes;

    @Override
    public String getAuthority() {
        return this.nome;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<UsuarioPermissao> getUsuariosPermissoes() {
        return usuariosPermissoes;
    }

    public void setUsuariosPermissoes(Set<UsuarioPermissao> usuariosPermissoes) {
        this.usuariosPermissoes = usuariosPermissoes;
    }

    // equals e hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permissao that)) return false;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNome(), that.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome());
    }
}
