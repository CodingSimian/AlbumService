package com.AlbumService.Controller;


import com.AlbumService.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/album")
public class AlbumController {
    //Ingen crud utöver get då albumservice ska endast presentera data den redan har, eller hämta ny data

    @Autowired
    AlbumService albumService;

    @GetMapping
    @RequestMapping("/Artists/{name}/albums")
    public ResponseEntity getAllAlbums(@PathVariable("name") String name){

        return new ResponseEntity(albumService.getAlbumsWithoutArtistObjects(name),HttpStatus.OK);
        //return new ResponseEntity(albumService.getAlbums(name),HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/Album/name/")
    public ResponseEntity getSpecificAlbum(){

        return new ResponseEntity(albumService.getSpecificAlbum("1"),HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/Artists/{name}/songs")
    public ResponseEntity getAllSongsFromArtist(@PathVariable("name") String name){

        return new ResponseEntity(albumService.getSongs(name),HttpStatus.OK);
    }


    @PutMapping
    @RequestMapping("/Albums/{id}/change-name")
    public ResponseEntity updateAlbumName(@PathVariable("id") String id, @RequestBody String newName){
        albumService.updateAlbumName(id, newName);
        return new ResponseEntity("Album successfully changed name to " + albumService.findSpecificAlbum(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @RequestMapping("/Albums/everything")
    public ResponseEntity getEverythingLol(){
        return new ResponseEntity(albumService.getEverything(),HttpStatus.OK);
    }
}
