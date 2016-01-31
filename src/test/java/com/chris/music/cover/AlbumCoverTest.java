package com.chris.music.cover;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.musicbrainz.controller.Recording;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.musicbrainz.model.searchresult.RecordingResultWs2;

/**
 *
 * @author christian
 */
public class AlbumCoverTest {

    private static final String QUERY = "\"I wanna rock\" AND artist:\"Twisted Sister\"";
    private static final String QUERY_2 = "\"Loyal\" AND artist:\"Chris Brown\"";
    private static final String QUERY_3 = "\"In Bloom\" AND artist:\"Nirvana\"";
    private static final String QUERY_4 = "\"Escape me\" AND artist:\"Tiesto\"";
    
    private Recording recording;
    
    @Before
    public void setUp(){
        // First instantiate a controller
        recording = new Recording();
        recording.search(QUERY_4);
    }
    
    @Test
    public void testFindingRecordings() {
        List<RecordingResultWs2> recordingResults = recording.getFullSearchResultList();
        recordingResults.stream().filter(r -> r.getRecording() != null).forEach(r -> {
            r.getRecording().getReleases().forEach(AlbumCoverTest::printRelease);
        });
    }
    
    public void testFindingRecordingsFiltered() {
        List<RecordingResultWs2> recordingResults = recording.getFullSearchResultList();
        recordingResults.stream().filter(r -> r.getRecording() != null).forEach(r -> {
            r.getRecording().getReleases().stream()
                    .filter(rel -> rel != null && rel.getArtistCreditString().isEmpty())
                    .filter(rel -> rel.getStatus() != null && rel.getStatus().equals("Official"))
                    .forEach(AlbumCoverTest::printRelease);
        });
    }

    private static void printRelease(ReleaseWs2 release) {
        String mbid = release.getId();
        String title = release.getTitle();
        String date = release.getDateStr();
        String status = release.getStatus();
        String artistCredit = release.getArtistCreditString();
        System.out.println(String.format(".:. MBID=http://coverartarchive.org/release/%s | Title=%s | Date=%s | Status=%s | Credits=%s", mbid, title, date, status, artistCredit));
    }
}
