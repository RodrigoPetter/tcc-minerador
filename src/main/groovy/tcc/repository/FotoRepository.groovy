package tcc.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import tcc.entity.Foto

@Transactional
@RepositoryRestResource(collectionResourceRel = "pessoa", path = "pessoa")
interface FotoRepository extends CrudRepository<Foto, Long> {

    Foto findById(@Param("Id") Long id)
}