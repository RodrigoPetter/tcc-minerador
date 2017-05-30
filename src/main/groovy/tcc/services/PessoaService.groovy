package tcc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.facebook.api.User
import org.springframework.stereotype.Service
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
    void verify(User facebookUser){

        Pessoa pessoa = getByFacebookID(facebookUser.getId())

        //se pessoa ainda nao cadastrada, entao cadastra
        if(Objects.isNull(pessoa)){
            pessoa = new Pessoa(facebookId: facebookUser.getId(),
                                nomeCompleto: facebookUser.firstName.concat(" ").concat(facebookUser.lastName))

            pessoaRepository.save(pessoa)

            println "Nova pessoa cadastrada"
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
