package tcc.controllers.facebook

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.ImageType
import org.springframework.social.facebook.api.PagingParameters
import org.springframework.social.facebook.api.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import tcc.services.PessoaService

@RestController
class Extrator {

    private Facebook facebook
    private UsersConnectionRepository usersConnectionRepository
    private PessoaService pessoaService

    @Autowired
    Extrator(Facebook facebook, UsersConnectionRepository usersConnectionRepository, PessoaService pessoaService) {
        this.facebook = facebook
        this.usersConnectionRepository = usersConnectionRepository
        this.pessoaService = pessoaService
    }

    @RequestMapping("/profile/data")
    Object getuser(){
        String [] fields = [ "id", "age_range", "email", "name","first_name", "last_name", "link"]
        User userProfile = facebook.fetchObject("me", User.class, fields)
        pessoaService.verify(userProfile, facebook)
        return userProfile
    }

    @RequestMapping("/profile/photo")
    String getFoto(){
        byte[] photo = facebook.userOperations().getUserProfileImage(ImageType.LARGE)
        return JsonOutput.toJson([photo:photo.encodeBase64().toString()])
    }

    @RequestMapping("/profile/albums")
    Object getAlbums(){
        return facebook.mediaOperations().getAlbums()
    }

    @RequestMapping("/albums/photos")
    Object getPhotos(@RequestParam("album-id") String albumId,
                     @RequestParam(value="limit", required = false) Integer limit){
        if(limit > 0){
            return facebook.mediaOperations().getPhotos(albumId, new PagingParameters(limit, null, null, null))
        }else{
            return facebook.mediaOperations().getPhotos(albumId)
        }
    }

    @RequestMapping("/single-photo-image")
    byte[] getSinglePhotoImage(@RequestParam("photo-id") String photoId){
        return facebook.mediaOperations().getPhotoImage(photoId)
    }

    @RequestMapping("/profile/friends")
    Object getFriends(){
        return facebook.friendOperations().getFriendIds().toArray()
    }

}
