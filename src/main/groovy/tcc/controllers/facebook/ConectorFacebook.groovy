package tcc.controllers.facebook

import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.facebook.api.Facebook
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/conector-facebook")
class ConectorFacebook {

    private Facebook facebook
    private ConnectionRepository connectionRepository

    ConectorFacebook(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook
        this.connectionRepository = connectionRepository
    }

    @GetMapping
    String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook"
        }

        return "redirect:/perfil/dados"
    }
}
