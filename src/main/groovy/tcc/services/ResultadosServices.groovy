package tcc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tcc.Classes.Aparicao
import tcc.controllers.facebook.Extrator
import tcc.entity.Pessoa
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository

import java.util.stream.Collectors

@Service
class ResultadosServices {

    @Autowired
    private FotoRepository fotoRepository
    @Autowired
    private PessoaRepository pessoaRepository
    @Autowired
    private Extrator extrator

    @Transactional(readOnly=true)
    Integer getTotalFotos(Pessoa pessoa){

        println("antes: "+pessoa.fotos.size())
        println("depois: "+pessoa.fotos.stream().filter{it.analisada}.collect(Collectors.toList()).size())
        return pessoa.fotos.stream().filter{it.analisada}.collect(Collectors.toList()).size()

    }

    List<Aparicao> getAparicoes(Pessoa pessoa){

        List<Aparicao> aparicoesTotal = new ArrayList<Aparicao>()

        ArrayList<String> amigos = extrator.getFriends()

        pessoa.fotos.removeIf{!it.analisada}

        pessoa.fotos.each { pessoasFoto ->

            pessoasFoto.compostaPor.each { pessoaFoto ->

                Aparicao aparicao = aparicoesTotal.find {it.nomeCompleto == pessoaFoto.nomeCompleto}

                println("Aparicao:")
                println(aparicao)

                if(aparicao){
                    aparicao.total += 1
                }else{
                    aparicao = new Aparicao(nomeCompleto: pessoaFoto.nomeCompleto)
                    aparicao.id = pessoaFoto.id
                    aparicao.facebookId = pessoaFoto.facebookId
                    aparicao.facebookProfilePhoto = pessoaFoto.facebookProfilePhoto
                    aparicao.total = 1
                    aparicao.isAmigo = (pessoaFoto.facebookId == pessoa.facebookId || amigos.find {it == pessoaFoto.facebookId})
                    aparicoesTotal.add(aparicao)
                }

            }

        }

        //ordena lista
        aparicoesTotal = aparicoesTotal.sort { -it.total }

        return aparicoesTotal
    }

    List<Aparicao> calcularPercentual(Pessoa pessoa, List<Aparicao> listaAparicoes){

        listaAparicoes.each {
            it.percentual = (it.total*100)/getTotalFotos(pessoa)
            println it.percentual
        }

        return listaAparicoes

    }

    Object getAnaliseCondicional(Pessoa owner, List<Aparicao> listAparicoes){

        Integer totalFotos =  this.getTotalFotos(owner)

        //3 pessoas que mais apareceram
        Aparicao aparicaoPessoa1 = listAparicoes.get(0)
        Pessoa pessoa1 = pessoaRepository.findById(aparicaoPessoa1.id)
        Aparicao aparicaoPessoa2 = listAparicoes.get(1)
        Pessoa pessoa2 = pessoaRepository.findById(aparicaoPessoa2.id)
        Aparicao aparicaoPessoa3 = listAparicoes.get(2)
        Pessoa pessoa3 = pessoaRepository.findById(aparicaoPessoa3.id)

        //variaveis prob. condicional
        Float Pa, Pb, Pc, PaUb, PaUc, PbUc, PaIb, PaUbUc
        String Prob1, Prob2

        //calcula o condicional do Top 2
        Pa = aparicaoPessoa1.total
        Pb = aparicaoPessoa2.total
        def lista1 = pessoa1.getApareceEm().stream().findAll {foto -> foto.compostaPor.find { pessoaFoto -> pessoaFoto.facebookId == pessoa2.facebookId}}
        PaUb = lista1.size()
        PaIb = PaUb / Pb
        Prob1 = Math.floor(PaIb*100)


        //calcula o condicional do Top 3
        Pa = PaIb
        Pb = aparicaoPessoa3.total
        PaUb = lista1.stream().findAll{foto -> foto.compostaPor.find { pessoaFoto -> pessoaFoto.facebookId == pessoa3.facebookId}}.size()
        PaIb = PaUb / Pb
        Prob2 = Math.floor(PaIb*100)


        def mensagem1 = 'Se '+pessoa1.nomeCompleto+' aparecer, existe '+Prob1+'% de chance de '+pessoa2.nomeCompleto+' também aparecer na mesma foto.'
        def mensagem2 = 'Se '+pessoa1.nomeCompleto+' e '+pessoa2.nomeCompleto+' aparecer, existe '+Prob2+'% de chance de '+pessoa3.nomeCompleto+' também aparecer na mesma foto.'

        println mensagem1
        println mensagem2

        return {["mensagem1":mensagem1, "mensagem2": mensagem2]}
    }

    Integer fotosConjuntas(Pessoa owner, Pessoa pessoa1, Pessoa pessoa2){
        return pessoa1.apareceEm.findAll {
            it in owner.fotos &&
            it.compostaPor.find {
                it.id == pessoa2.id
            }
        }.size()
    }
}
