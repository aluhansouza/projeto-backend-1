package api.integrationtests.dto;

import java.io.Serializable;
import java.util.Objects;

public class EquipamentoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    public EquipamentoDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EquipamentoDTO that = (EquipamentoDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "EquipamentoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
