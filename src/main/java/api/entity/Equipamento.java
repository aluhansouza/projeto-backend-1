package api.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Table(name = "tb_equipamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;





}
