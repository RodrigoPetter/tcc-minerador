package tcc.controllers

import groovy.json.JsonSlurper
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.entity.Classifier
import tcc.entity.Foto
import tcc.entity.Pessoa
import tcc.repository.ClassifierRepository
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository

import javax.xml.bind.DatatypeConverter

@RestController
@RequestMapping("/pessoa")
class Amizades {

    @Autowired
    private PessoaRepository PR

    @RequestMapping(value="addAmizade", method=RequestMethod.GET)
    String processarImagem(@RequestParam("pessoaId1") Long pessoaId1, @RequestParam("pessoaId2") Long pessoaId2){
        try {
            Pessoa p1 = PR.findById(pessoaId1)
            Pessoa p2 = PR.findById(pessoaId2)

            p1.amizades.add(p2)
            p2.amizades.add(p1)

            PR.save(p1)
            PR.save(p2)

            return "Amizade criada com sucesso."

        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "Erro ao adicionar amizade: "+exception
        }
    }

}
