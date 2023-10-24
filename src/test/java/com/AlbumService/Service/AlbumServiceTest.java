package com.AlbumService.Service;

import com.AlbumService.Model.Album;
import com.AlbumService.Model.Artist;
import com.AlbumService.Model.Song;
import com.AlbumService.Repository.AlbumRepository;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AlbumServiceTest {
    @InjectMocks
    AlbumService albumService;

    @MockBean
    AlbumRepository albumRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveSongToAlbum() {
        // Create a sample album and song
        Album sampleAlbum = new Album();
        sampleAlbum.set_id(new ObjectId());
        sampleAlbum.setAlbumIdentification("1");
        sampleAlbum.setName("Sample Album");
        sampleAlbum.setSongs(new ArrayList<>());

        Song sampleSong = new Song();
        sampleSong.setAlbum_ord(0);
        sampleSong.setName("Sample Song");
        Artist artist = new Artist();
        artist.setName("Sample Artist");
        sampleSong.setArtists(Collections.singletonList(artist));

        // Mock the behavior of the albumRepository
        when(albumRepository.save(sampleAlbum)).thenReturn(sampleAlbum);

        // Call the method under test
        albumService.saveSongToAlbum(sampleAlbum, sampleSong);

        // Assert that the song has been added to the album's songs list
        assertEquals("Sample Song(Sample Artist)", sampleAlbum.getSongs().get(0));
    }

    @Test
    void getAlbums() {
    }

    @Test
    void getSpecificAlbum() {
    }

    @Test
    void getSongs() {
    }

    @Test
    void getAlbum() {
    }

    @Test
    public void testUpdateAlbumName() {
        // Create a sample album
        Album sampleAlbum = new Album();
        sampleAlbum.set_id(new ObjectId());
        sampleAlbum.setAlbumIdentification("1");
        sampleAlbum.setName("Sample Album");

        // Mock the behavior of the albumRepository
        when(albumRepository.findAlbumByAlbumIdentification("1")).thenReturn(sampleAlbum);
        when(albumRepository.save(sampleAlbum)).thenReturn(sampleAlbum);

        // Call the method under test
        albumService.updateAlbumName("1", "New Name");

        // Assert that the album's name has been updated
        assertEquals("New Name", sampleAlbum.getName());
    }


    @Test
    public void testFindSpecificAlbum() {
        // Create a sample album
        Album sampleAlbum = new Album();
        sampleAlbum.set_id(new ObjectId());
        sampleAlbum.setAlbumIdentification("1");
        sampleAlbum.setName("Sample Album");

        // Mock the behavior of the albumRepository
        when(albumRepository.findAlbumByAlbumIdentification("1")).thenReturn(sampleAlbum);

        // Call the method under test
        Album foundAlbum = albumService.findSpecificAlbum("1");

        // Assert the result
        assertEquals(sampleAlbum, foundAlbum);
    }
}