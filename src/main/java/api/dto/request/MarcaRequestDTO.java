package api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@Relation(collectionRelation = "marcas")
public class MarcaRequestDTO extends RepresentationModel<MarcaRequestDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 1, max = 50, message = "Nome deve ter entre 1 e 50 caracteres")
    private String nome;

    public MarcaRequestDTO() {}

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
        if (!(o instanceof MarcaRequestDTO)) return false;
        MarcaRequestDTO that = (MarcaRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "MarcaRequestDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
