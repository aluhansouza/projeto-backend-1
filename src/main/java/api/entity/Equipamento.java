package api.entity;

import api.entity.audit.Auditable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;


@Table(name = "tb_equipamentos")
@Entity
public class Equipamento  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    private String sobreNome;

    @Column(name = "qrcode_valor", nullable = true)
    private String qrcodeValor;

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

    public Equipamento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getQrcodeValor() {
        return qrcodeValor;
    }

    public void setQrcodeValor(String qrcodeValor) {
        this.qrcodeValor = qrcodeValor;
    }

    public Equipamento() {}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento that = (Equipamento) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(qrcodeValor, that.qrcodeValor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, qrcodeValor);
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", qrcodeValor='" + qrcodeValor + '\'' +
                '}';
    }
}
