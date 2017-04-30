package tcc.controllers

import tcc.entity.Pessoa

import javax.xml.bind.DatatypeConverter
import groovy.json.JsonSlurper
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.entity.Foto
import tcc.entity.Classifier
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository
import tcc.repository.ClassifierRepository

@RestController
@RequestMapping("/minerador")
class Minerador {

    @Autowired
    private PessoaRepository PR
    @Autowired
    private FotoRepository FR
    @Autowired
    private ClassifierRepository CR
    private URL url = new URL("http://192.168.0.16:8081/RPC2")
    private XmlRpcClient client = new XmlRpcClient()

    @RequestMapping(method=RequestMethod.GET)
    Object processarImagem(@RequestParam("foto-id") Long id, @RequestParam("classifier-id") Long classifierId){
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
            config.setServerURL(url)

            client.setConfig(config)

            Classifier c = CR.findById(classifierId)
            Foto f = FR.findById(id)

            Vector params = new Vector()
            params.addElement(Base64.encoder.encodeToString(c.arquivo))
            params.addElement(Base64.encoder.encodeToString(f.imagem))

            String result = client.execute("descobrir", params)
            result = result.replace(",]", "]")

            System.out.println("Resultado: "+ result)

            return result
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "Erro ao chamar servidor rpc do openface: "+exception
        }
    }

    @RequestMapping(value="treinar", method=RequestMethod.GET)
    String treinar(){

        try {
            //apaga tabela de classifiers
            CR.deleteAll()

            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
            config.setServerURL(url)

            XmlRpcClient client = new XmlRpcClient()
            client.setConfig(config)

            Vector params = new Vector()

            Object result = client.execute("treinar", params)

            def jsonSlurper = new JsonSlurper()
            def object = jsonSlurper.parseText(result)

            Classifier c = new Classifier()
            c.setNome(object.result.get(0).nome)
            DatatypeConverter.parseBase64Binary(object.result.get(0).arquivo)
            c.setArquivo(DatatypeConverter.parseBase64Binary(object.result.get(0).arquivo))
            CR.save(c)

            c = new Classifier()
            c.setNome(object.result.get(1).nome)
            c.setArquivo(DatatypeConverter.parseBase64Binary(object.result.get(1).arquivo))
            CR.save(c)

            c = new Classifier()
            c.setNome(object.result.get(2).nome)
            c.setArquivo(DatatypeConverter.parseBase64Binary(object.result.get(2).arquivo))
            CR.save(c)

            c = new Classifier()
            c.setNome(object.result.get(3).nome)
            c.setArquivo(DatatypeConverter.parseBase64Binary(object.result.get(3).arquivo))
            CR.save(c)

            System.out.println("Treinamento concluído.")

            return "Treinamento concluído."
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "Erro ao chamar servidor rpc do openface: "+exception.stackTrace
        }

    }

    @RequestMapping(value="salvarResultado", method=RequestMethod.GET)
    String salvarResultado(@RequestParam("foto-id") Long fotoId, @RequestParam("pessoa-nome") String pessoaNome){

        Pessoa p = PR.findByNomeCompleto(pessoaNome)

        println("Pessoa encontrada: "+p.nomeCompleto)

        Foto f = FR.findById(fotoId)

        p.apareceEm.add(f)
        f.analisada = true

        PR.save(p)
        FR.save(f)

        return "Relacionamento salvo com sucesso."

    }

    @RequestMapping(value="testar", method=RequestMethod.GET)
    String testar(){

        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
            config.setServerURL(url)

            XmlRpcClient client = new XmlRpcClient()
            client.setConfig(config)

            Vector params = new Vector()

            params.addElement(new Integer(17))
            params.addElement(new Integer(13))

            Object result = client.execute("exemplo", params)

            int sum = ((Integer) result).intValue()
            System.out.println("The sum is: "+ sum)

            return sum
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "Erro ao chamar servidor rpc do openface: "+exception
        }

    }

}
