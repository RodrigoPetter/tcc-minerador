package tcc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import tcc.entity.Pessoa

@Transactional
@RepositoryRestResource(collectionResourceRel = "pessoa", path = "pessoa")
interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByNomeCompleto(@Param("name") String nomeCompleto)
    Pessoa findById(@Param("id") Long id)
}