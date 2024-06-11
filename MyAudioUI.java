
/*
 * Name: Suboohi Sayeed
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI {
	public static void main(String[] args) {
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your
		// mylibrary
		AudioContentStore store = new AudioContentStore();

		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try {
				String action = scanner.nextLine();
				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("STORE")) // List all songs
				{
					store.listAll();
				} else if (action.equalsIgnoreCase("SONGS")) // List all songs
				{
					mylibrary.listAllSongs();
				} else if (action.equalsIgnoreCase("BOOKS")) // List all songs
				{
					mylibrary.listAllAudioBooks();
				} else if (action.equalsIgnoreCase("PODCASTS")) // List all songs
				{
					mylibrary.listAllPodcasts();
				} else if (action.equalsIgnoreCase("ARTISTS")) // List all songs
				{
					mylibrary.listAllArtists();
				} else if (action.equalsIgnoreCase("PLAYLISTS")) // List all play lists
				{
					mylibrary.listAllPlaylists();
				}
				// Download audiocontent (song/audiobook/podcast) from the store
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) {
					int startIndex = 0;
					int endIndex = 0;

					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt()) {
						startIndex = scanner.nextInt();
						scanner.nextLine();
						System.out.print("To Store Content #: ");
						endIndex = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					// loop within the range of the indices specified by the user 
					for (int i = startIndex; i <= endIndex; i++) {
						AudioContent content = store.getContent(i);	// get the AudioContent at each index
						try {
							if (content == null) {
								throw new ContentNotFoundInStore("Content Not Found in Store");
							}
							mylibrary.download(content);
							System.out.println(content.getType().toUpperCase() + " " + content.getTitle() + " Added to the Library");
						} catch (AudioContentAlreadyDownloadedException e) {
							System.out.println(e.getMessage());
						} catch (ContentNotFoundInStore e1) {
							System.out.println(e1.getMessage());
						}
					}
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song
				else if (action.equalsIgnoreCase("PLAYSONG")) {
					try {
						System.out.print("Song Number: ");
						int index = scanner.nextInt();
						scanner.nextLine();
						mylibrary.playSong(index);
					// catch AudioContentNotFoundException if the song doesn't exist in the library
					} catch (AudioContentNotFoundException exception) {
						System.out.println(exception.getMessage());
					}

				}
				// Print the table of contents (TOC) of an audiobook that
				// has been downloaded to the library. Get the desired book index
				// from the keyboard - the index is based on the list of books in the library
				else if (action.equalsIgnoreCase("BOOKTOC")) {
					System.out.print("AudioBook Number: ");
					int index = scanner.nextInt();
					scanner.nextLine();
					// catch AudioContentNotFoundException if the book doesn't exist in the library
					try {
						mylibrary.printAudioBookTOC(index);
					} catch (AudioContentNotFoundException e) {
						System.out.println(e.getMessage());
					}

				}
				// Similar to playsong above except for audio book
				// In addition to the book index, read the chapter
				// number from the keyboard - see class Library
				else if (action.equalsIgnoreCase("PLAYBOOK")) {
					System.out.print("Audio Book Number: ");
					int index = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Chapter: ");
					int chapter = scanner.nextInt();
					scanner.nextLine();
					try {
						mylibrary.playAudioBook(index, chapter);
					} catch (AudioContentNotFoundException e) {
						System.out.println(e.getMessage());
					} catch (ChapterNotFound e1) {
						System.out.println(e1.getMessage());
					}
				}
				 // Print the episode titles for the given season of the given podcast
				 // In addition to the podcast index from the list of podcasts,
				 // read the season number from the keyboard
				 // see class Library for the method to call
				 else if (action.equalsIgnoreCase("PODTOC"))
				 {
					try {
						System.out.print("Podcast Number: ");
						int index = scanner.nextInt();
						System.out.print("Season: ");
						int season = scanner.nextInt();
						mylibrary.printPodcastEpisodes(index, season);
					} catch (AudioContentNotFoundException exception){
						System.out.println(exception.getMessage());
					}
				 }
				 // Similar to playsong above except for podcast
				 // In addition to the podcast index from the list of podcasts,
				 // read the season number and the episode number from the keyboard
				 // see class Library for the method to call
				 else if (action.equalsIgnoreCase("PLAYPOD"))
				 {
					try {
						System.out.print("Podcast Number: ");
						int index = scanner.nextInt();
						System.out.print("Season: ");
						int season = scanner.nextInt();
						System.out.print("Episode: ");
						int episode = scanner.nextInt();
						mylibrary.playPodcast(index, season, episode);
					} catch (AudioContentNotFoundException exception){
						System.out.println(exception.getMessage());
					}
				 }
				 
				// Specify a playlist title (string)
				// Play all the audio content (songs, audiobooks, podcasts) of the playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYALLPL")) {
					try {
						System.out.print("Playlist Title: ");
						String title = scanner.nextLine();
						mylibrary.playPlaylist(title);
					} catch (TitleNotFoundException exception) {
						System.out.println(exception.getMessage());
					}
				}
				// Specify a playlist title (string)
				// Read the index of a song/audiobook/podcast in the playist from the keyboard
				// Play all the audio content
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PLAYPL")) {
					try {
						System.out.print("Playlist Title: ");
						String title = scanner.nextLine();
						System.out.print("Content Number: ");
						int index = scanner.nextInt();
						scanner.nextLine();
						mylibrary.playPlaylist(title, index);
					} catch (AudioContentNotFoundException exception1) {
						System.out.println(exception1.getMessage());
					} catch (TitleNotFoundException exception2) {
						System.out.println(exception2.getMessage());
					}
				}
				// Delete a song from the list of songs in mylibrary and any play lists it
				// belongs to
				// Read a song index from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELSONG")) {
					try {
						System.out.print("Library Song #: ");
						int index = scanner.nextInt();
						scanner.nextLine();
						mylibrary.deleteSong(index);
					} catch (AudioContentNotFoundException exception) {
						System.out.println(exception.getMessage());
					}
				}
				// Read a title string from the keyboard and make a playlist
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("MAKEPL")) {
					System.out.print("Playlist Title: ");
					String title = scanner.nextLine();
					try {
						mylibrary.makePlaylist(title);
					} catch (DuplicatePlaylist exception4) {
						System.out.println(exception4.getMessage());
					}

				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
				// Read a playlist title string from the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("PRINTPL")) // print playlist content
				{
					try {
						System.out.print("Playlist Title: ");
						String title = scanner.nextLine();
						mylibrary.printPlaylist(title);
					} catch (TitleNotFoundException e1) {
						System.out.println(e1.getMessage());
					}
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a
				// playlist
				// Read the playlist title, the type of content ("song" "audiobook" "podcast")
				// and the index of the content (based on song list, audiobook list etc) from
				// the keyboard
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("ADDTOPL")) {
					try {
						System.out.print("Playlist Title: ");
						String title = scanner.nextLine();
						System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
						String type = scanner.nextLine();
						System.out.print("Library Content #: ");
						int contentNum = scanner.nextInt();
						scanner.nextLine();
						mylibrary.addContentToPlaylist(type, contentNum, title);
					} catch (InvalidTypeException e1) {
						System.out.println(e1.getMessage());
					} catch (TitleNotFoundException e2) {
						System.out.println(e2.getMessage());
					} catch (AudioContentNotFoundException e3) {
						System.out.println(e3.getMessage());
					}

				}
				// Delete content from play list based on index from the playlist
				// Read the playlist title string and the playlist index
				// see class Library for the method to call
				else if (action.equalsIgnoreCase("DELFROMPL")) {
					try {
						System.out.print("Playlist Title: ");
						String title = scanner.nextLine();
						System.out.print("Playlist Content #: ");
						int index = scanner.nextInt();
						scanner.nextLine();
						mylibrary.delContentFromPlaylist(index, title);
					} catch (AudioContentNotFoundException e1) {
						System.out.println(e1.getMessage());
					} catch (TitleNotFoundException e2) {
						System.out.println(e2.getMessage());
					}
				}

				else if (action.equalsIgnoreCase("SEARCH")) {
					try {
						System.out.print("Title: ");
						String title = scanner.nextLine();
						// call the search method from AudioContentStore
						store.searchTitle(title);
					} catch (ContentNotFoundInStore e) {
						System.out.println(e.getMessage());
					}
				} else if (action.equalsIgnoreCase("SEARCHA")) {
					try {
						System.out.print("Artist: ");
						String artist = scanner.nextLine();
						// iterate through the array list returned by the searchA method in AudioContentStore
						// the array list contains indices of contents that are from the specified artist
						for (int index : store.searchA(artist)) {
							System.out.print((index + 1) + ". ");
							// print out the AudioContent info
							store.getContent(index + 1).printInfo();
							System.out.println();
						}
					} catch (ContentNotFoundInStore e) {
						System.out.println(e.getMessage());
					}
				} else if (action.equalsIgnoreCase("SEARCHG")) {
					try {
						System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
						String genre = scanner.nextLine();
						// iterate through the array list returned by the searchG method in AudioContentStore
						// the array list contains indices of contents that are of the specified genre
						for (int index : store.searchG(genre)) {
							System.out.print((index + 1) + ". ");
							// print the AudioContent info
							store.getContent(index + 1).printInfo();
							System.out.println();
						}
					} catch (ContentNotFoundInStore e2) {
						System.out.println(e2.getMessage());
					}
				} else if (action.equalsIgnoreCase("SEARCHP")) {
					try{
						System.out.print("Target: ");
						String target = scanner.nextLine();
						// call the searchP method defined in AudioContentStore
						store.searchP(target);
					} catch (ContentNotFoundInStore e1){
						System.out.println(e1.getMessage());
					}
				} else if (action.equalsIgnoreCase("DOWNLOADA")) {
					try {
						System.out.print("Artist Name: ");
						String artist = scanner.nextLine();
						// iterate through the array list returned by the searchA method in AudioContentStore
						// for every AudioContent that contains the specified artist, call the download method on it
						for (int index : store.searchA(artist)) {
							AudioContent content = store.getContent(index + 1);
							if (content == null) {
								throw new ContentNotFoundInStore("Content Not Found in Store");
							}
							try {
								mylibrary.download(content);
							} catch (AudioContentAlreadyDownloadedException e) {
								System.out.println(e.getMessage());
							}
						}
					} catch (ContentNotFoundInStore e1){
						System.out.println(e1.getMessage());
					}
				} else if (action.equalsIgnoreCase("DOWNLOADG")) {
					System.out.print("Genre: ");
					String genre = scanner.nextLine();
					try{
						// iterate through the array list returned by the searchG method in AudioContentStore
						// for every AudioContent that contains the specified genre, call the download method on it
						for (int index : store.searchG(genre)) {
							AudioContent content = store.getContent(index + 1);
							if (content == null) {
								throw new ContentNotFoundInStore("Content Not Found in Store");
							}
							try {
								mylibrary.download(content);
							} catch (AudioContentAlreadyDownloadedException e) {
								System.out.println(e.getMessage());
							}
						}
					} catch(ContentNotFoundInStore e1){
						System.out.println(e1.getMessage());
					} 
				} else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				} else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}

				System.out.print("\n>");
			} catch (AudioContentNotFoundException exception1) {
				System.out.println(exception1.getMessage());
			} catch (AudioContentAlreadyDownloadedException exception2) {
				System.out.println(exception2.getMessage());
			} catch (TitleNotFoundException exception3) {
				System.out.println(exception3.getMessage());
			} catch (DuplicatePlaylist exception4) {
				System.out.println(exception4.getMessage());
			} catch (InputMismatchException exception5) {
				System.out.println("Please enter a valid input and try again!");
			} catch (ContentNotFoundInStore exception6) {
				System.out.println(exception6.getMessage());
			}
		}

	}
}
