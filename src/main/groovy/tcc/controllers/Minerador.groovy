package tcc.controllers

import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.entity.Foto
import tcc.repository.FotoRepository
import tcc.repository.PessoaRepository

@RestController
@RequestMapping("/minerador")
class Minerador {

    @Autowired
    private PessoaRepository pr
    @Autowired
    private FotoRepository FR
    private URL url = new URL("http://192.168.0.16:8081/RPC2")
    private XmlRpcClient client = new XmlRpcClient()

    @RequestMapping(method=RequestMethod.GET)
    Object processarImagem(@RequestParam("foto-id") Long id){
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl()
            config.setServerURL(url)

            client.setConfig(config)

            Foto f = FR.findById(id)

            Vector params = new Vector()
            params.addElement(Base64.encoder.encodeToString(f.imagem))

            String result = client.execute("descobrir", params)
            result = result.replace(",]", "]")

            System.out.println("Resultado: "+ result)

            return result
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception)
        }
    }

    @RequestMapping(value="teste", method=RequestMethod.GET)
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
        }

        return "aaaaaaaa"

    }

}
