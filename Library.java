/*
 * Name: Suboohi Sayeed
 */
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library {
	private ArrayList<Song> songs;
	private ArrayList<AudioBook> audiobooks;
	private ArrayList<Playlist> playlists;

	private ArrayList<Podcast> podcasts;

	// Public methods in this class throw exceptions

	public Library() {
		songs = new ArrayList<Song>();
		audiobooks = new ArrayList<AudioBook>();
		;
		playlists = new ArrayList<Playlist>();
		podcasts = new ArrayList<Podcast>();
		;
	}
	/*
	 * Download audio content from the store. Since we have decided (design
	 * decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list)
	 * then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or
	 * AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already
	 * there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false.
	 * Otherwise add it to the list and return true
	 * See the video
	 */

	public void download(AudioContent content) { 
		// check if the downloaded content is of type Song
		if (content.getType().equals(Song.TYPENAME)) {
			Song song = (Song) content; // cast the Audiocontent object to a Song object
			if (!songs.contains(song)) {
				// if the song doesn't already exist in the list then add the new Song object
				songs.add(song);
			} else if (songs.contains(song)) {
				// if the Song object already exists in the list then throw an exception
				throw new AudioContentAlreadyDownloadedException("Song " + song.getTitle() + " already downloaded");
			}

		} // check if the downloaded content is of type Audiobook
		else if (content.getType().equals(AudioBook.TYPENAME)) {
			AudioBook audiobook = (AudioBook) content; // cast the Audiocontent object to a Audiobook object
			if (!audiobooks.contains(audiobook)) {
				// if the audiobook doesn't already exist in the list then add the new Audiobook object
				audiobooks.add(audiobook);
			} else {
				// if the Audiobook object already exists in the list then throw an exception
				throw new AudioContentAlreadyDownloadedException("AudioBook " + audiobook.getTitle() + " already downloaded");
			}

		}
		// check if the downloaded content is of type Podcast
		else if (content.getType().equals(Podcast.TYPENAME)) {
			Podcast podcast = (Podcast) content; // cast the Audiocontent object to a Podcast object
			if (!podcasts.contains(podcast)) {
				// if the podcast doesn't already exist in the list then add the new Podcast object
				podcasts.add(podcast);
			}
			// if the Podcast object already exists in the list then throw an exception
			else {
				throw new AudioContentAlreadyDownloadedException("Podcast " + podcast.getTitle() + " already downloaded");
			}
		}

	}

	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs() {
		// go through the songs arraylist
		for (int i = 0; i < songs.size(); i++) {
			// each song is numbered starting at the index 1
			int index = i + 1;
			System.out.print("" + index + ". "); // Print the index of each Song object in the list
			songs.get(i).printInfo(); // Use the printInfo method defined in the Song class to print details about
										// each song
			System.out.println();
		}
	}

	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks() {
		// go through the audiobooks arraylist
		for (int i = 0; i < audiobooks.size(); i++) {
			// each song is numbered starting at the index 1
			int index = i + 1;
			System.out.print("" + index + ". "); // Print the index of each Audiobook object in the list
			audiobooks.get(i).printInfo(); // Use the printInfo method defined in the Audiobook class to print details
											// about each audiobook
			System.out.println();
		}
	}

	// Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts() {
		// go through the podcasts arraylist
		for (int i = 0; i < podcasts.size(); i++) {
			// each podcast is indexed starting at the index 1
			int index = i + 1;
			System.out.print("" + index + ". "); // Print the index of each Podcast object in the list
			podcasts.get(i).printInfo(); // Use the printInfo method defined in the Podcast class to print details about
											// each podcast
			System.out.println();
		}
	}

	// Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists() {
		// go through the arraylist of playlists
		for (int i = 0; i < playlists.size(); i++) {
			// each playlist is indexed starting at the index 1
			int index = i + 1;
			System.out.print("" + index + ". "); // Print the index of each Playlist object in the list
			System.out.println(playlists.get(i).getTitle()); // Use the getTitle method in the Playlist class to print
																// details about the playlist
		}
	}

	// Print the name of all artists.
	public void listAllArtists() {
		// First create a new (empty) array list of string
		// Go through the songs array list and add the artist name to the new arraylist
		// only if it is not already there. Once the artist arrayl ist is complete,
		// print
		// the artists names
		ArrayList<String> newArray = new ArrayList<String>();
		newArray.add(songs.get(0).getArtist()); // add the first Artist Name to the empty array list
		for (int i = 0; i < songs.size(); i++) {
			for (int j = 0; j < newArray.size(); j++) { // iterate through the new array list
				// if the new array list does not contain the Artist name then add it to the
				// list
				if (newArray.contains(songs.get(i).getArtist()) == false) {
					// get the Artist name from the current song and add to the new array list
					newArray.add(songs.get(i).getArtist());
				}
			}
		}
		// print out the Artist Names in the new array list
		for (int j = 0; j < newArray.size(); j++) {
			System.out.println(j + 1 + ". " + newArray.get(j));
		}

	}

	// Delete a song from the library (i.e. the songs list) -
	// also go through all playlists and remove it from any playlist as well if it
	// is part of the playlist
	public void deleteSong(int index) {
		// throw an exception if the given song index does not exist in the list
		if (index < 1 || index > songs.size()) { 
			throw new AudioContentNotFoundException("Song Not Found");
		}
		Song content = songs.get(index - 1); // access the specific song from the songs array list
		songs.remove(index - 1); // remove the specific song from the songs array list
		// iterate through the playlists to check if the specific song exist in a playlist
		for (int playlist = 0; playlist < playlists.size(); playlist++) {
			// iterate through the content array list of each playlist
			for (int i = 0; i < playlists.get(playlist).getContent().size(); i++) {
				// check if the content is of the type Song
				if (playlists.get(playlist).getContent().get(i).getType().equals(Song.TYPENAME)) {
					// check if the playlist contains the specific song
					if (playlists.get(playlist).getContent().get(i).equals(content)) {
						// if it does, then delete the specific song from the playlist
						playlists.get(playlist).deleteContent(i + 1);
					}
				}
			}
		}
	}

	// Sort songs in library by year
	public void sortSongsByYear() {
		// Use Collections.sort()
		Collections.sort(songs, new SongYearComparator());

	}

	// Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song> {
		public int compare(Song song1, Song song2) {
			if ((song1.getYear()) > (song2.getYear())) {
				return 1; // song1 was released earlier
			} else if (song1.getYear() < song2.getYear()) {
				return -1; // song2 was released earlier
			} else {
				return 0; // both songs are released during the same year
			}

		}

	}

	// Sort songs by length
	public void sortSongsByLength() {
		// Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());
	}

	// Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song> {
		public int compare(Song song1, Song song2) {
			if ((song1.getLength()) > (song2.getLength())) {
				return 1; // song1 is longer
			} else if (song1.getLength() < song2.getLength()) {
				return -1; // song2 is longer
			} else {
				return 0; // both songs are the same length
			}
		}
	}

	// Sort songs by title
	public void sortSongsByName() {
		// Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		// Collections.sort();
		Collections.sort(songs);
	}

	/*
	 * Play Content
	 */

	// Play song from songs list
	public void playSong(int index) {
		// check if the index is within the range of the songs array list
		// if the given song index does not exist in the list throw an exception
		if (index < 1 || index > songs.size()) { 
			throw new AudioContentNotFoundException("Song Not Found");
		}
		songs.get(index - 1).play(); // play the song at the specified index
	}

	 // Play podcast from list (specify season and episode)
	 // Bonus
	 public void playPodcast(int index, int season, int episode)
	 { // check if the index is within the range of the podcasts indices
		if (index < 1 || index > podcasts.size()){ 
			// set the errorMsg if the given podcast index does not exist in the list
			throw new AudioContentNotFoundException("Podcast Not Found");
		}
		// set the season of the podcast at the specified index
		podcasts.get(index - 1).setSeason(season - 1);
		// set the episode of the podcast at the specified index
		podcasts.get(index - 1).setEpisode(episode - 1);
		// play the the specific podcast using the index given
		podcasts.get(index - 1).play();
	 }
	  
	 // Print the episode titles of a specified season
	 // Bonus
	 public void printPodcastEpisodes(int index, int season)
	 { // check if the index is within the range of the podcasts indices
		if (index < 1 || index > podcasts.size()){ 
			// set the errorMsg if the given podcast index does not exist in the list
			throw new AudioContentNotFoundException("Podcast Not Found");
		} else{
		// set the season of the podcast at the specified index
		podcasts.get(index - 1).setSeason(season - 1);
		// use the printTOC() method from the Podcast class to print out the titles
		podcasts.get(index - 1).printTOC();
	 }
	  
	}
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) { 
		// check if the index is within the range of the audiobooks indices
		// if the given audiobooks index does not exist in the list then throw an exception
		if (index < 1 || index > audiobooks.size()) { 
			throw new AudioContentNotFoundException("AudioBook Not Found");
		}
		// set the currentChapter to the given chapter index from the chapter array list
		audiobooks.get(index - 1).selectChapter(chapter);
		// play the specific chapter
		audiobooks.get(index - 1).play();

	}

	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) { 
		// check if the index is within the range of the audiobooks indices
		// if the given audiobooks index does not exist in the list then throw an exception
		if (index < 1 || index > audiobooks.size()) {
			throw new AudioContentNotFoundException("AudioBook Not Found");
		} else {
			// use the printTOC() method from the Audiobook class to print out the title
			audiobooks.get(index - 1).printTOC();
		}
	}

	/*
	 * Playlist Related Methods
	 */

	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) { 
		// create a new Playlist object which takes the title as the parameter
		Playlist p = new Playlist(title);
		for (int i = 0; i < playlists.size(); i++) {
			// check each playlist in the playlists array list to see if the title already exists
			if (playlists.get(i).getTitle().equals(p.getTitle())) {
				throw new DuplicatePlaylist("Playlist " + title + " Already Exists");
			}
		}
		// if the given playlist title does not already exist in the list,
		// then add the playlist object to the playlists array list
		playlists.add(p);
	}

	// Print list of content information (songs, audiobooks etc) in playlist named
	// title from list of playlists
	public void printPlaylist(String title) {
		// use a boolean variable to throw an exception if the playlist title does not exist
		boolean found = false;
		for (int i = 0; i < playlists.size(); i++) {
			//check if the given playlist title exists in the list
			if (playlists.get(i).getTitle().equals(title)) {
				// if it exists, then use the printContents method from the Playlist class to
				// print out the information about each content in the array list
				playlists.get(i).printContents();
				found = true;
			}
		}
		// if the playlist title is not found, throw an exception
		if (!found) {
			throw new TitleNotFoundException("Playlist " + title + " Was Not Found");
		}
	}

	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) {
		// use a boolean variable to throw an exception if the playlist title does not exist
		boolean found = false;
		for (int i = 0; i < playlists.size(); i++) {
			// check if the given playlist title exists in the list
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				// if it exists, then play all the content in the specified playlist using the
				// playAll() method defined in the Playlist class
				playlists.get(i).playAll();
				found = true;
			}
		}
		// if the playlist title is not found, throw an exception
		if (!found) {
			throw new TitleNotFoundException("Playlist " + playlistTitle + " Was Not Found");
		}
	}

	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) {
		// use a boolean variable to throw an exception if the playlist title does not exist
		boolean found = false;
		System.out.println(playlistTitle);
		for (int i = 0; i < playlists.size(); i++) {
			if (playlists.get(i).getTitle().equals(playlistTitle)) {
				// use the play() method defined in the Playlist class to play the AudioContent
				// at the specified index
				playlists.get(i).play(indexInPL);
				found = true;
			}
		}
		// if the playlist title is not found, throw an exception
		if (!found) {
			throw new TitleNotFoundException("Playlist " + playlistTitle + " Was Not Found");
		}
	}

	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) {
		// use a boolean variable to throw an exception if the playlist title does not exist
		boolean found = false;
		if (type.equalsIgnoreCase(Song.TYPENAME)) {
			 // if the given song index does not exist in the list, throw an exception
			if (index < 1 || index > songs.size()) {
				throw new AudioContentNotFoundException("Song Not Found");
			}
			// If the content type is a song then create a new Song object to access the
			// song at the specified index in the songs array list
			Song newSong = songs.get(index - 1);
			// Cast the song to an AudioContent because only objects of type AudioContent can be added to a playlist
			AudioContent songContent = (AudioContent) newSong;
			// iterate through the playlists array list
			for (int i = 0; i < playlists.size(); i++) {
				// check if the given playlist title exists in the list
				if (playlists.get(i).getTitle().equals(playlistTitle)) {
					// if it exists, then add the song to the playlist using the addContent method
					// defined in the Playlist class
					playlists.get(i).addContent(songContent);
					found = true;
				}
			}
		} else if (type.equalsIgnoreCase(AudioBook.TYPENAME)) {
			// if the given audiobook index does not exist in the list, throw an exception
			if (index < 1 || index > audiobooks.size()) { 
				throw new AudioContentNotFoundException("AudioBook Not Found");
			}
			// Create a new AudioBook object to access the audiobook at the specified index in the audiobooks array list
			AudioBook book = audiobooks.get(index - 1);
			// Cast the audiobook to an AudioContent because only objects of type AudioContent can be added to a playlist
			AudioContent bookContent = (AudioContent) book;
			// check if the given playlist title exists in the list
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getTitle().equals(playlistTitle)) {
					// if it exists, then add the audiobook to the playlist using the addContent method
					// defined in the Playlist class
					playlists.get(i).addContent(bookContent);
					found = true;
				}
			}
		} else if (type.equalsIgnoreCase(Podcast.TYPENAME)) {
			// If the content type is a podcast then create a new Podcast object to access
			// the podcast
			// at the specified index in the podcasts array list
			Podcast podcast = podcasts.get(index - 1);
			// Cast the podcast to an AudioContent
			AudioContent podcastContent = (AudioContent) podcast;
			// check if the given playlist title exists in the list
			for (int i = 0; i < playlists.size(); i++) {
				if (playlists.get(i).getTitle().equals(playlistTitle)) {
					// if it exists, then add the podcast to the playlist using the addContent
					// method defined in the Playlist class
					playlists.get(i).addContent(podcastContent);
					found = true;
				}
			}
		} else {
			throw new InvalidTypeException("Please enter a valid content type");
		}
		// if the playlist title is not found, throw an exception
		if (!found) {
			throw new TitleNotFoundException("Playlist " + playlistTitle + " Was Not Found");
		}
	}

	// Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is
	// valid
	public void delContentFromPlaylist(int index, String title) {
		// use a boolean variable to throw an exception if the playlist title does not exist
		boolean found = false;
		for (int i = 0; i < playlists.size(); i++) {
			// check each playlist to see if it contains the content using given the index and title
			if (playlists.get(i).contains(index) && playlists.get(i).getTitle().equals(title)) {
				// use the deleteContent method of the Playlist class to remove the object from
				// the playlist
				playlists.get(i).deleteContent(index);
				found = true;
			} else if (playlists.get(i).contains(index) == false) {
				throw new AudioContentNotFoundException("Content does not exist");
			}
		}
		// if the playlist title is not found, throw an exception
		if (!found) {
			throw new TitleNotFoundException("Playlist " + title + " does not exist");
		}

	}

}
// EXCEPTION CLASSES

// This exception is thrown when the given index is not within the range of an array list
class AudioContentNotFoundException extends RuntimeException {
	public AudioContentNotFoundException(String message) {
		super(message);
	}

}
// This exception is thrown when the AudioContent has already been added to the library
class AudioContentAlreadyDownloadedException extends RuntimeException {
	public AudioContentAlreadyDownloadedException(String message) {
		super(message);
	}

}
// This exception is thrown when the playlist with the given title is not in the list
class TitleNotFoundException extends RuntimeException {
	public TitleNotFoundException(String message) {
		super(message);
	}

}
// This exception is thrown when a playlist with the same title already exists
class DuplicatePlaylist extends RuntimeException {
	public DuplicatePlaylist(String message) {
		super(message);
	}

}
// This exception is thrown in AudioContentStore when the search methods cannot locate any matches
class ContentNotFoundInStore extends RuntimeException {
	public ContentNotFoundInStore(String message) {
		super(message);
	}
}
// This exception is thrown in AudioBook.java when the specified chapter index is not within the range 
// of the chapters array list indices
class ChapterNotFound extends RuntimeException {
	public ChapterNotFound(String message) {
		super(message);
	}
}
// This exception is thrown when the user does not provide an appropriate type name
class InvalidTypeException extends RuntimeException {
	public InvalidTypeException(String message) {
		super(message);
	}
}