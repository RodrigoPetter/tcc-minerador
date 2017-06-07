package tcc.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tcc.Classes.Aparicao
import tcc.entity.Pessoa
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository

import java.text.Format

@Service
class ResultadosServices {

    @Autowired
    private FotoRepository fotoRepository
    @Autowired
    private PessoaRepository pessoaRepository

    Integer getTotalFotos(Pessoa pessoa){

        println("antes: "+pessoa.fotos.size())
        pessoa.fotos.removeIf{!it.analisada}
        println("depois: "+pessoa.fotos.size())
        return pessoa.fotos.size()

    }

    List<Aparicao> getAparicoes(Pessoa pessoa){

        List<Aparicao> aparicoesTotal = new ArrayList<Aparicao>()

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
                    aparicao.total = 1
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

    String getAnaliseCondicional(Pessoa owner, List<Aparicao> listAparicoes){

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
        Pa = aparicaoPessoa1.total / totalFotos
        Pb = aparicaoPessoa2.total / totalFotos
        PaUb = Pa*Pb
        PaIb = PaUb / Pb
        Prob1 = Math.floor(PaIb*100)

        //calcula o condicional do Top 3
        Pc = aparicaoPessoa3.total / totalFotos
        PaUc = Pa*Pc
        PbUc = Pb*Pc
        PaUbUc = Pa*Pb*Pc


        println 'Se '+pessoa1.nomeCompleto+' aparecer, existe '+Prob1+'% de chance de'+pessoa2.nomeCompleto+' também aparecer na mesma foto.'

        return null
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
