package com.AlbumService.Service;

import com.AlbumService.Model.Album;
import com.AlbumService.Model.Media;
import com.AlbumService.Model.Song;
import com.AlbumService.Repository.AlbumRepository;
import org.bson.types.ObjectId;
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

    public void saveSongToAlbum(Album albumToBeSaved,Song songToBeAdded){
        //Songs are saved in a specific order in the arraylist representing the album
        //Artist names are added to the song name, as part of the string.
        ArrayList<String> artistNames = new ArrayList<>();
        for(int i = 0; i < songToBeAdded.getArtists().size(); i++){
            artistNames.add(songToBeAdded.getArtists().get(i).getName());
        }

    albumToBeSaved.getSongs().add(songToBeAdded.getAlbum_ord(),songToBeAdded.getName() + "(" + artistNames + ")");
    albumRepository.save(albumToBeSaved);
    }

    public void saveMediaToAlbum(Album albumToBeSaved,Media songToBeAdded){
        //Songs are saved in a specific order in the arraylist representing the album
        //Artist names are added to the song name, as part of the string.
        ArrayList<String> artistNames = new ArrayList<>();
        for(int i = 0; i < songToBeAdded.getArtists().size(); i++){
            artistNames.add(songToBeAdded.getArtists().get(i));
        }

        albumToBeSaved.getSongs().add(songToBeAdded.getAlbum_ord(),songToBeAdded.getTitle() + "(" + artistNames + ")");
        albumRepository.save(albumToBeSaved);
    }


    public List<Album> getAlbums(String name) {
            //Basically this method should take the return value from media service, in where it delivers all songs relating
            //to the given artist AND none of the given songs are singles/releases.

            //TODO include exception handling for if the sought artists albums already exist in the db

            ResponseEntity<List<Song>> songResponse = restTemplate.exchange("http://localhost:8081/media/" + name + "/allMedia/song", HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Song>>() {
                    });
            List<Song> someMoreSongsAgain = songResponse.getBody();

            for (int i = 0; i < someMoreSongsAgain.size(); i++) {

                //Method call necessary because if album not found, one should be created
                Album albumToBeSaved = getAlbum(someMoreSongsAgain.get(i));


                ArrayList<String> artistNames = new ArrayList<>();
                for (int b = 0; b < someMoreSongsAgain.get(i).getArtists().size(); b++) {
                    artistNames.add(someMoreSongsAgain.get(i).getArtists().get(b).getName());
                }

                //Specific if statement for if songs are to be saved to an album
                if (!albumToBeSaved.getSongs().contains(someMoreSongsAgain.get(i).getName() + "(" + artistNames + ")")) {
                    saveSongToAlbum(albumToBeSaved, someMoreSongsAgain.get(i));
                }

            }
            return albumRepository.findAlbumsByArtistName(name);

    }


    public List<Album> getAlbumsWithoutArtistObjects(String name) {
        //Basically this method should take the return value from media service, in where it delivers all songs relating
        //to the given artist AND none of the given songs are singles/releases.

        //TODO include exception handling for if the sought artists albums already exist in the db

        ResponseEntity<List<Media>> songResponse = restTemplate.exchange("http://localhost:8081/" + name + "/allMedia/song", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Media>>() {
                });
        List<Media> someMoreSongsAgain = songResponse.getBody();

        for (int i = 0; i < someMoreSongsAgain.size(); i++) {

            //Method call necessary because if album not found, one should be created
            Album albumToBeSaved = getAlbumButForMedia(someMoreSongsAgain.get(i));


            ArrayList<String> artistNames = new ArrayList<>();
            for (int b = 0; b < someMoreSongsAgain.get(i).getArtists().size(); b++) {
                artistNames.add(someMoreSongsAgain.get(i).getArtists().get(b));
            }

            //Specific if statement for if songs are to be saved to an album
            if (!albumToBeSaved.getSongs().contains(someMoreSongsAgain.get(i).getTitle() + "(" + artistNames + ")")) {
                saveMediaToAlbum(albumToBeSaved, someMoreSongsAgain.get(i));
            }

        }
        return albumRepository.findAlbumsByArtistName(name);

    }

        public Album getSpecificAlbum (String id){
            return albumRepository.findAlbumByAlbumIdentification(id);
        }

        public Object getSongs (String name){
            //List<Song> allValues =restTemplate.getForObject("http://localhost:8585/snongs/" + name, List.class);
            String allValues = restTemplate.getForObject("http://localhost:8585/snongs/" + name, String.class);

            return allValues;
        }


    public Album getAlbumButForMedia(Media songUsedToRetrieveAlbum) {
        try {
            //If mongorepository does not find a entity in its repository, it will create a empty <Optional> object.
            //This try and catch block would normally not catch any nullpointerexception errors because in
            //java's eyes there is no error occuring. There is simply a empty object being returned.
            Album albumToBeSaved = albumRepository.findAlbumByAlbumIdentification(String.valueOf(songUsedToRetrieveAlbum.getAlbum_id()));

            //To truly check if the object is empty a variable must be assigned to one of the properties of the object.
            //Only now, can we simulate a nullpointerexception error
            String name = albumToBeSaved.getName();
            return albumToBeSaved;
        } catch (NullPointerException e) {
            System.out.println("Album was not registered in db");

            Album albumToBeSaved = new Album();
            //Let the artist responsible for the album be the first registrered artist for the first song
            //on that album
            albumToBeSaved.setArtistName(songUsedToRetrieveAlbum.getArtists().get(0));
            albumToBeSaved.set_id(new ObjectId());
            albumToBeSaved.setAlbumIdentification(String.valueOf(songUsedToRetrieveAlbum.getAlbum_id()));
            albumToBeSaved.setName("AlbumNr" + songUsedToRetrieveAlbum.getAlbum_id());
            albumToBeSaved.setSongs(new ArrayList<>());

            return albumToBeSaved;
        }
    }


        public Album getAlbum(Song songUsedToRetrieveAlbum){
        try{
            //If mongorepository does not find a entity in its repository, it will create a empty <Optional> object.
            //This try and catch block would normally not catch any nullpointerexception errors because in
            //java's eyes there is no error occuring. There is simply a empty object being returned.
            Album albumToBeSaved = albumRepository.findAlbumByAlbumIdentification(String.valueOf(songUsedToRetrieveAlbum.getAlbum_id()));

            //To truly check if the object is empty a variable must be assigned to one of the properties of the object.
            //Only now, can we simulate a nullpointerexception error
            String name = albumToBeSaved.getName();
            return albumToBeSaved;
        }catch (NullPointerException e){
            System.out.println("Album was not registered in db");

            Album albumToBeSaved = new Album();
            //Let the artist responsible for the album be the first registrered artist for the first song
            //on that album
            albumToBeSaved.setArtistName(songUsedToRetrieveAlbum.getArtists().get(0).getName());
            albumToBeSaved.set_id(new ObjectId());
            albumToBeSaved.setAlbumIdentification(String.valueOf(songUsedToRetrieveAlbum.getAlbum_id()));
            albumToBeSaved.setName("AlbumNr" + songUsedToRetrieveAlbum.getAlbum_id());
            albumToBeSaved.setSongs(new ArrayList<>());

            return albumToBeSaved;
        }


        }

    public void updateAlbumName(String id, String newName) {
        Album albumToBeUpdated = albumRepository.findAlbumByAlbumIdentification(id);
        albumToBeUpdated.setName(newName);
        albumRepository.save(albumToBeUpdated);
    }


    public Album findSpecificAlbum(String id){
        return albumRepository.findAlbumByAlbumIdentification(id);
    }

    public String getEverything() {
        ResponseEntity<List<Media>> mediaResponse = restTemplate.exchange("http://localhost:8081/everything", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Media>>() {
                });
        List<Media> someMoreMediaAgain = mediaResponse.getBody();

        return someMoreMediaAgain.get(0).getId().toHexString();

    }
}


