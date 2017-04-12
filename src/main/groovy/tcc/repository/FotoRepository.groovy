package tcc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import tcc.entity.Foto

@Transactional
@RepositoryRestResource(collectionResourceRel = "foto", path = "foto")
interface FotoRepository extends JpaRepository<Foto, Long> {

    Foto findById(@Param("id") Long id)
}