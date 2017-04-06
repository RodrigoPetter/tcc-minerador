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
@Table(name="fotos")
class Foto implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    String path

    @ManyToOne
    @JoinColumn(name="owner")
    Pessoa owner

    @ManyToMany
    @JoinTable(name="fotos_pessoas",
            joinColumns=@JoinColumn(name="foto_id"),
            inverseJoinColumns=@JoinColumn(name="pessoa_id")
    )
    List<Pessoa> compostaPor
}