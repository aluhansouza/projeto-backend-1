package api.entity;

import api.entity.audit.Auditable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_marca")
public class Marca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 38)
    private String nome;

    public Marca() {}

    public Marca(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Marca)) return false;
        Marca marca = (Marca) o;
        return Objects.equals(id, marca.id) &&
                Objects.equals(nome, marca.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
