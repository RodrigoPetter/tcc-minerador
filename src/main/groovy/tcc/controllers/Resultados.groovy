package tcc.controllers

import groovy.json.JsonSlurper
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.Classes.Aparicao
import tcc.entity.Classifier
import tcc.entity.Foto
import tcc.entity.Pessoa
import tcc.repository.ClassifierRepository
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository
import tcc.services.ResultadosServices

import javax.xml.bind.DatatypeConverter

@RestController
@RequestMapping("/resultados")
class Resultados {

    @Autowired
    private PessoaRepository PR
    @Autowired
    private FotoRepository FR
    @Autowired
    private ResultadosServices resultadosServices

    @RequestMapping(value="/{pessoaId}/aparicoes", method=RequestMethod.GET)
    List<Aparicao> getResultados(@PathVariable Long pessoaId){

        Pessoa pessoa = PR.findById(pessoaId)

        List<Aparicao> aparicoes = resultadosServices.getAparicoesAmigos(pessoa)

        aparicoes = resultadosServices.calcularPercentual(pessoa, aparicoes)

        return aparicoes
    }

    @RequestMapping(value="/{pessoaId}/total-fotos", method=RequestMethod.GET)
    Integer getTotalFotos(@PathVariable Long pessoaId){

        Pessoa pessoa = PR.findById(pessoaId)

        return resultadosServices.getTotalFotos(pessoa)
    }
}
