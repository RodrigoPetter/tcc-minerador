package tcc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping
import tcc.entity.Foto

@Transactional
@RepositoryRestResource(collectionResourceRel = "foto", path = "foto")
interface FotoRepository extends JpaRepository<Foto, Long> {

    Foto findById(@Param("id") Long id)
    List<Foto> findByAnalisada(@Param("analisada") boolean analisada)
    Foto findByFacebookId(@Param("id") String id)
}