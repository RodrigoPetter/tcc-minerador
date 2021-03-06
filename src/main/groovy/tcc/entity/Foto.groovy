package tcc.entity

import groovy.transform.Canonical
import javassist.bytecode.ByteArray

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Canonical
@Entity
@Table(name="fotos")
class Foto implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @Column(length = 20971520)
    byte[] imagem

    String facebookAlbumId

    String facebookId

    @Column(nullable = false)
    @NotNull
    boolean analisada = false

    @ManyToOne
    @JoinColumn(name="owner")
    @NotNull
    Pessoa owner

    @ManyToMany(cascade =[CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH])
    @JoinTable(name="fotos_pessoas",
            joinColumns=@JoinColumn(name="foto_id"),
            inverseJoinColumns=@JoinColumn(name="pessoa_id")
    )
    List<Pessoa> compostaPor
}