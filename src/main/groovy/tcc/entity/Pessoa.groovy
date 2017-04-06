package tcc.entity

import groovy.transform.Canonical

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

@Canonical
@Entity
@Table(name = "pessoas")
class Pessoa implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id
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

    @ManyToMany(mappedBy="compostaPor")
    List<Foto> apareceEm
}