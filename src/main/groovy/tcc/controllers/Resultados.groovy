package tcc.controllers

import groovy.json.JsonSlurper
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.Classes.Aparicao
import tcc.controllers.facebook.Extrator
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
    @Autowired
    private Extrator extrator

    @RequestMapping(value="/{pessoaId}/total-fotos", method=RequestMethod.GET)
    Integer getTotalFotos(@PathVariable Long pessoaId){

        Pessoa pessoa = PR.findById(pessoaId)

        return resultadosServices.getTotalFotos(pessoa)
    }

    @RequestMapping(value="/{pessoaId}/aparicoes", method=RequestMethod.GET)
    List<Aparicao> getResultados(@PathVariable Long pessoaId){

        Pessoa pessoa = PR.findById(pessoaId)

        List<Aparicao> aparicoes = resultadosServices.getAparicoes(pessoa)

        aparicoes = resultadosServices.calcularPercentual(pessoa, aparicoes)

        return aparicoes
    }

    @RequestMapping(value="/{pessoaId}/condicional", method=RequestMethod.POST)
    Object getAnaliseCondicional(@PathVariable Long pessoaId,
                                 @RequestBody List<Aparicao> listAparicoes){
        Pessoa owner = PR.findById(pessoaId)
        return resultadosServices.getAnaliseCondicional(owner, listAparicoes)

    }
    @RequestMapping(value="/currentUserId/{facebookId}", method=RequestMethod.GET)
    Object getcurrentUserId(@PathVariable String facebookId){
        Pessoa pessoa = PR.findByFacebookId(facebookId)
        return '{"currentUserId": '+pessoa.getId()+'}'

    }


}
