package tcc.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tcc.entity.Pessoa
import tcc.repository.PessoaRepository

@RestController
@RequestMapping("/Minerador")
class Minerador {

    @Autowired
    private PessoaRepository pr

    @RequestMapping(method=RequestMethod.GET)
    List<Pessoa> getPessoas(){

        return pr.find()

    }
}
