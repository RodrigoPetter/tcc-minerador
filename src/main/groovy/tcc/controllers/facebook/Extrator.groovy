package tcc.controllers.facebook

import groovy.json.JsonOutput
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

    @RequestMapping("/profile/data")
    Object getuser(){
        String [] fields = [ "id", "age_range", "email", "first_name", "last_name", "link"]
        User userProfile = facebook.fetchObject("me", User.class, fields)
        facebook.userOperations().getUserProfileImage()
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
    Object getPhotos(@RequestParam("album-id") String albumId){
        return facebook.mediaOperations().getPhotos(albumId)
    }

    @RequestMapping("/profile/friends")
    Object getFriends(){
        return facebook.friendOperations().getFriendIds()
    }
}
