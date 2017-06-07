package tcc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping
import tcc.entity.Foto
import tcc.entity.Pessoa

@Transactional
@RepositoryRestResource(collectionResourceRel = "foto", path = "foto")
interface FotoRepository extends JpaRepository<Foto, Long> {

    Foto findById(@Param("id") Long id)
    List<Foto> findByAnalisada(@Param("analisada") boolean analisada)
    Foto findByFacebookId(@Param("id") String id)

    //@Query(value = "SELECT COUNT(1) FROM fotos_pessoas f inner join fotos_pessoas f2 ON(f.foto_id = f2.foto_id) WHERE f.pessoa_id = ?1 AND f2.pessoa_id = ?2")
    //@Query(value = "SELECT p.id, fp.foto_id, p2.id FROM pessoas p INNER JOIN pessoas p2 INNER JOIN (SELECT f.foto_id, f.pessoa_id as pess1, f2.pessoa_id as pess2 FROM fotos_pessoas f inner join fotos_pessoas f2 ON(f.foto_id = f2.foto_id)) fp ON(fp.pess1 = p.id AND fp.pess2=p2.id) where p.id = 7 AND p2.id = 8")
    //Integer aparicoesConjuntas(@Param("idPessoa1") Long idPessoa1, @Param("idPessoa2") Long idPessoa2)
}