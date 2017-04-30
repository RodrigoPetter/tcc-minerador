package tcc.services

import org.springframework.stereotype.Service
import tcc.Classes.Aparicao
import tcc.entity.Pessoa

@Service
class ResultadosServices {

    Integer getTotalFotos(Pessoa pessoa){

        println("antes: "+pessoa.fotos.size())
        pessoa.fotos.removeIf{!it.analisada}
        println("depois: "+pessoa.fotos.size())
        return pessoa.fotos.size()

    }

    List<Aparicao> getAparicoesAmigos(Pessoa pessoa){

        List<Aparicao> aparicoesTotal = new ArrayList<Aparicao>()

        pessoa.fotos.removeIf{!it.analisada}

        pessoa.fotos.each { pessoasFoto ->

            pessoasFoto.compostaPor.removeIf{it.nomeCompleto == pessoa.nomeCompleto || !pessoa.amizades.contains(it)}

            pessoasFoto.compostaPor.each { pessoaFoto ->

                Aparicao aparicao = aparicoesTotal.find {it.nomeCompleto == pessoaFoto.nomeCompleto}

                println("Aparicao:")
                println(aparicao)

                if(aparicao){
                    aparicao.total += 1
                }else{
                    aparicao = new Aparicao(nomeCompleto: pessoaFoto.nomeCompleto)
                    aparicao.total = 1
                    aparicoesTotal.add(aparicao)
                }

            }

        }

        return aparicoesTotal
    }

    List<Aparicao> calcularPercentual(Pessoa pessoa, List<Aparicao> listaAparicoes){

        listaAparicoes.each {
            it.percentual = (it.total*100)/getTotalFotos(pessoa)
        }

        return listaAparicoes

    }
}