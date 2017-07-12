package tcc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.ImageType
import org.springframework.social.facebook.api.User
import org.springframework.stereotype.Service
import tcc.controllers.facebook.Extrator
import tcc.entity.Pessoa
import tcc.repository.PessoaRepository

import javax.transaction.Transactional

@Service
class PessoaService {

    PessoaRepository pessoaRepository

    @Autowired
    PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository
    }

    @Transactional
    void verify(User facebookUser, Facebook facebook){

        Pessoa pessoa = getByFacebookID(facebookUser.getId())

        //se pessoa ainda nao cadastrada, entao cadastra
        if(Objects.isNull(pessoa)){
            pessoa = new Pessoa(facebookId: facebookUser.getId(),
                                nomeCompleto: facebookUser.name,
                                facebookProfilePhoto: facebook.userOperations().getUserProfileImage(ImageType.LARGE).encodeBase64().toString())

            pessoaRepository.save(pessoa)

            println "Nova pessoa cadastrada"

            //manda fotos para treinamento
            def albumPerfil = facebook.mediaOperations().getAlbums().find {it.name == "Profile Pictures"}
            def listaFotos = facebook.mediaOperations().getPhotos(albumPerfil.getId())

        }

    }

    Pessoa getByName(String first, String last){

        String nomeCompleto

        if(last.isEmpty()){
            nomeCompleto = first
        }else{
            nomeCompleto = first.concat(" ").concat(last)
        }

        return pessoaRepository.findByNomeCompletoLikeIgnoreCase('%'+nomeCompleto+'%')
    }

    Pessoa getByFacebookID(String facebookID){
        return pessoaRepository.findByFacebookId(facebookID)
    }
}
