package tcc.controllers

import org.springframework.social.facebook.api.Photo
import tcc.controllers.facebook.Extrator
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
    @Autowired
    private Extrator extrator
    private URL url = new URL("http://192.168.0.14:8081/RPC2")
    private XmlRpcClient client = new XmlRpcClient()
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @RequestMapping(method=RequestMethod.GET)
    Object processarImagem(@RequestParam("profile-id") String profileId,
                           @RequestParam("album-id") String albumId,
                           @RequestParam("foto-id") String facebookPhotoID,
                           @RequestParam("classifier-id") Long classifierId){
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
            config.setServerURL(url)

            client.setConfig(config)

            Classifier c = CR.findById(classifierId)

            byte[] image = extrator.getSinglePhotoImage(facebookPhotoID)

            Vector params = new Vector()
            params.addElement(c.arquivo.encodeBase64().toString())
            params.addElement(image.encodeBase64().toString())

            String result = client.execute("descobrir", params)
            result = result.replace(",]", "]")

            def json = jsonSlurper.parseText(result)

            //após processamento, salva o registro da foto no banco se nao existir
            Foto foto = FR.findByFacebookId(facebookPhotoID)
            if(Objects.isNull(foto)) {
                json.isAnalisada = false
                Pessoa pessoa = PR.findByFacebookId(profileId)
                foto = new Foto(facebookAlbumId: albumId, facebookId: facebookPhotoID, owner: pessoa, analisada: false)
                FR.save(foto)
            }else{
                json.isAnalisada = foto.analisada
            }

            return json
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "{'erro': Erro ao chamar servidor rpc do openface: "+exception+"}"
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

            c = new Classifier()
            c.setNome(object.result.get(4).nome)
            c.setArquivo(DatatypeConverter.parseBase64Binary(object.result.get(4).arquivo))
            CR.save(c)

            System.out.println("Treinamento concluído.")

            return "Treinamento concluído."
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
            return "Erro ao chamar servidor rpc do openface: "+exception.stackTrace
        }

    }

    @RequestMapping(value="salvarResultado", method=RequestMethod.GET)
    Object salvarResultado(@RequestParam("foto-id") String facebookPhotoID,
                           @RequestParam("pessoa-nome") String pessoaNome){

        pessoaNome = "%"+pessoaNome+"%"

        Pessoa p = PR.findByNomeCompletoLikeIgnoreCase(pessoaNome)
        if(Objects.isNull(p)){
            return '{"message":"Erro: Pessoa '+pessoaNome+' não está cadastrada."}'
        }

        Foto f = FR.findByFacebookId(facebookPhotoID)
        if(Objects.isNull(f)){
            return '{"message":"Erro: Foto id '+facebookPhotoID+' não encontrada."}'
        }

        f.compostaPor.add(p)
        f.analisada = true

        FR.save(f)

        return '{"message":"Informações salvas com sucesso!"}'

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

    @RequestMapping(value="apagar-base", method = RequestMethod.DELETE)
    Object apagarBase(){
        FR.deleteAll()
        return '{"message": "Dados Apagados."}'
    }
}
