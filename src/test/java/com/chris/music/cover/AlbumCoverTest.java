package com.chris.music.cover;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

    private static final String QUERY_1 = "\"I wanna rock\" AND artist:\"Twisted Sister\"";
    private static final String QUERY_2 = "\"Loyal\" AND artist:\"Chris Brown\"";
    private static final String QUERY_3 = "\"In Bloom\" AND artist:\"Nirvana\"";
    private static final String QUERY_4 = "\"Escape me\" AND artist:\"Tiësto\"";
    private static final String QUERY_5 = "\"Satellite\" AND artist:\"Rise Against\"";
    
    private Recording recording;
    
    @Before
    public void setUp(){
        // First instantiate a controller
        recording = new Recording();
        recording.search(QUERY_5);
    }
    
    
    @Test
    public void testGroupingRecordings() {
    	final int limit = 10;
    	final String artistName = "Chris Brown";
    	final String songName = "Loyal";
    	String query = String.format("\"%s\" AND artist:\"%s\"", songName, artistName);
    	System.out.println(query);
		
    	recording.search(query);
    	
    	List<RecordingResultWs2> recordingResults = recording.getFullSearchResultList();
    	
    	// Sort all the albums from the one that is repeated the most to the least
    	Map<String, Long> releasesMap = recordingResults.stream()
    	.map(RecordingResultWs2::getRecording)
    	.flatMap(rec -> rec.getReleases().stream())
    	.filter(rel -> "Official".equalsIgnoreCase(rel.getStatus()))
    	.filter(rel -> {
    		String artist = rel.getArtistCreditString();
    		if(artist != null && !artist.isEmpty())
    		{
    			return artist.toLowerCase().contains(artistName.toLowerCase());
    		}
    		return true;
    	})
    	.collect(Collectors.groupingBy(ReleaseWs2::getTitle, Collectors.counting()))
    	.entrySet().stream()
    	.sorted((es1, es2) -> Long.compare(es2.getValue(), es1.getValue()))
    	.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (k,v) -> {throw new IllegalStateException("Duplicate key:" + k);}, LinkedHashMap::new));
    	
    	releasesMap.forEach((k , v) -> System.out.println(v + "::" + k));
    	
    	// Get all the Releases
    	List<ReleaseWs2> allReleases = recordingResults.stream()
    	    	.map(RecordingResultWs2::getRecording)
    	    	.flatMap(rec -> rec.getReleases().stream())
    	    	.collect(Collectors.toList());
    	
		// Collect in a list all the Releases that are the most relevant
		List<ReleaseWs2> relevantReleases = releasesMap.entrySet().stream()
				.flatMap(es -> allReleases.stream().filter(rel -> rel.getTitle().equals(es.getKey())))
				.limit(limit)
				.collect(Collectors.toList());
    	
    	relevantReleases.forEach(AlbumCoverTest::printRelease);
    	
    	
    }
    
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
