package tcc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.transaction.annotation.Transactional
import tcc.entity.Classifier

@Transactional
@RepositoryRestResource(collectionResourceRel = "classifier", path = "classifier")
interface ClassifierRepository extends JpaRepository<Classifier, Long> {

    Classifier findById(@Param("id") Long id)
}