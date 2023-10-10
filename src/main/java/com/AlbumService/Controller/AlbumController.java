package com.AlbumService.Controller;


import com.AlbumService.Service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlbumController {
    //Ingen crud utöver get då albumservice ska endast presentera data den redan har, eller hämta ny data

    @Autowired
    AlbumService albumService;

    @GetMapping
    @RequestMapping("/Artists/{name}/albums")
    public ResponseEntity getAllAlbums(@PathVariable("name") String name){

        return new ResponseEntity(albumService.getAlbums(name),HttpStatus.OK);
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

}
