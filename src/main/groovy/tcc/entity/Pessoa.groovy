package tcc.entity

import groovy.transform.Canonical

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull

@Canonical
@Entity
@Table(name = "pessoas")
class Pessoa implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    String facebookId

    @Column(nullable = false, unique = true)
    @NotNull
    String nomeCompleto

    @ManyToMany
    @JoinTable(name="amizades",
            joinColumns=@JoinColumn(name="pessoaId"),
            inverseJoinColumns=@JoinColumn(name="amigoId")
    )
    List<Pessoa> amizades

    @ManyToMany
    @JoinTable(name="amizades",
            joinColumns=@JoinColumn(name="amigoId"),
            inverseJoinColumns=@JoinColumn(name="pessoaId")
    )
    List<Pessoa> amigoDe

    @OneToMany
    @JoinColumn(name="owner")
    List<Foto> fotos

    @ManyToMany
    @JoinTable(name="fotos_pessoas",
            joinColumns=@JoinColumn(name="pessoa_id"),
            inverseJoinColumns=@JoinColumn(name="foto_id")
    )
    List<Foto> apareceEm
}