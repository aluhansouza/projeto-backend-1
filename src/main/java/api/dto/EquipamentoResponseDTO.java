package api.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@Relation(collectionRelation = "equipamentos")
public class EquipamentoResponseDTO extends RepresentationModel<EquipamentoResponseDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String qrcodeValor;

    public EquipamentoResponseDTO() {
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

    public String getQrcodeValor() {
        return qrcodeValor;
    }

    public void setQrcodeValor(String qrcodeValor) {
        this.qrcodeValor = qrcodeValor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EquipamentoResponseDTO that = (EquipamentoResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(qrcodeValor, that.qrcodeValor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome, qrcodeValor);
    }

    @Override
    public String toString() {
        return "EquipamentoResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", qrcodeValor='" + qrcodeValor + '\'' +
                '}';
    }
}


