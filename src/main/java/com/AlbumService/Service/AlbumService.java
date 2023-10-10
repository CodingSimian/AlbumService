package com.AlbumService.Service;

import com.AlbumService.Model.Album;
import com.AlbumService.Model.Song;
import com.AlbumService.Repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    public RestTemplate restTemplate;

    public void saveSongToAlbum(Album albumToBeSaved,String songName){
    albumToBeSaved.getSongs().add(songName);
    albumRepository.save(albumToBeSaved);
    }

    public List<Album> getAlbums(String name) {
        //Basically this method should take the return value from media service, in where it delivers all songs relating
        //to the given artist AND none of the given songs are singles/releases.

        //Now, this return value could either be treated as a String, or as seprate objects. However the presentation
        //only really needs to give 1. the album name, 2. The song name. 3. The songs relating artists.

        //Also if you just handle the string AS IS, then album service in of itself doesn't need any real mvc architecture
        //Just a pojo object with a string property.

        //TODO include exception handling for if the sought artists albums already exist in the db

        ResponseEntity<List<Song>> songResponse = restTemplate.exchange("http://localhost:8585/snongs/" + name, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Song>>() {
        });
        List<Song> someMoreSongsAgain = songResponse.getBody();

    //ArrayList<Song> allValues =restTemplate.get("http://localhost:8585/snongs/" + name, ArrayList.class);
        //String allValues =restTemplate.getForObject("http://localhost:8585/snongs/" + name, String.class);
        for(int i =0; i < someMoreSongsAgain.size(); i++){
            String album_id = String.valueOf(someMoreSongsAgain.get(i).getAlbum_id());
            Album albumToBeSaved = albumRepository.findAlbumByAlbumIdentification(album_id);


        //writing if(!albumToBeSaved.getSongs().contains(allValues.get(i).getName()) is the same as writing
            //if(albumToBeSaved.getSongs().contains(allValues.get(i).getName()) == false


            //TODO change the logic in this method, for some reasons all the songs are added even if they are already part
            //of the album
            if(albumToBeSaved.getSongs().contains(someMoreSongsAgain.get(i).getName()) == false){
            saveSongToAlbum(albumToBeSaved, someMoreSongsAgain.get(i).getName());
        }

        }
        return albumRepository.findAll();
    }

    public Album getSpecificAlbum(String id){
        return albumRepository.findAlbumByAlbumIdentification(id);
    }

    public Object getSongs(String name) {
        //List<Song> allValues =restTemplate.getForObject("http://localhost:8585/snongs/" + name, List.class);
        String allValues =restTemplate.getForObject("http://localhost:8585/snongs/" + name, String.class);

        return allValues;
    }
}
