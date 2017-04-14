package tcc.entity

import groovy.transform.Canonical

import javax.persistence.*
import javax.validation.constraints.NotNull

@Canonical
@Entity
@Table(name="treinamento")
class Classifier implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long id

    @NotNull
    @Column(length = 20971520, nullable = false)
    byte[] arquivo

    @Column(nullable = false)
    @NotNull
    String nome
}