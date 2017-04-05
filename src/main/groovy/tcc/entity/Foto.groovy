package tcc.entity

import groovy.transform.Canonical

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Canonical
@Entity
@Table(name="fotos")
class Foto implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    String path

    @OneToOne
    Pessoa owner

    /*@OneToMany
    List<Pessoa> compostaPor*/
}