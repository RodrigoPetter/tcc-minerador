package tcc.controllers.facebook

import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.ImageType
import org.springframework.social.facebook.api.User
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class Extrator {

    private Facebook facebook
    private UsersConnectionRepository usersConnectionRepository

    Extrator(Facebook facebook, UsersConnectionRepository usersConnectionRepository) {
        this.facebook = facebook
        this.usersConnectionRepository = usersConnectionRepository
    }

    @RequestMapping("/perfil/dados")
    Object getuser(){
        usersConnectionRepository.findUserIdsWithConnection()
        String [] fields = [ "id", "age_range", "email", "first_name", "last_name", "link"]
        User userProfile = facebook.fetchObject("me", User.class, fields)
        facebook.userOperations().getUserProfileImage()
        return userProfile
    }

    @RequestMapping("/perfil/foto")
    Object getFoto(){
        return facebook.userOperations().getUserProfileImage(ImageType.LARGE)
    }

    @RequestMapping("/perfil/albums")
    Object getAlbums(){
        return facebook.mediaOperations().getAlbums()
    }

    @RequestMapping("/perfil/photos")
    Object getPhotos(@RequestParam("album-id") String albumId){
        return facebook.mediaOperations().getPhotos(albumId)
    }

    @RequestMapping("/perfil/friends")
    Object getFriends(){
        return facebook.friendOperations().getFriendIds()
    }
}
