/*
 * Name: Suboohi Sayeed
 * Student ID: 501175964
 */
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore {
	private ArrayList<AudioContent> contents = new ArrayList<>();
	private Map<String, Integer> titleMap = new TreeMap<String, Integer>();	// Map that stores the titles of all AudioContent
	private Map<String, ArrayList<Integer>> artistMap = new TreeMap<>(); // Map that stores the indices containing the string names of artists
	private Map<String, ArrayList<Integer>> genreMap = new TreeMap<>(); // Map that stores the indices containing the string genres of all AudioContent

	public AudioContentStore() {
		// call the getAudioContentFile method defined below to read the store.txt file
		getAudioContentFile();
		// Initialize the maps using the contents array list
		for (int i = 0; i < contents.size(); i++) {
			titleMap.put(contents.get(i).getTitle(), i);

			if (contents.get(i).getType().equals(Song.TYPENAME)) {
				Song song = (Song) contents.get(i);
				// If the map contains the artist already then add the index of the content to the integer array list
				if (artistMap.containsKey(song.getArtist())) {
					ArrayList<Integer> integers = artistMap.get(song.getArtist());
					integers.add(i);

				} else {
					// If the map does not contain the artist then create a new integer array
					// list and add it into the map
					ArrayList<Integer> integers = new ArrayList<>();
					integers.add(i);
					artistMap.put(song.getArtist(), integers);
				}
				// If the map contains the genre already then add the index of the content to the genreIndex array list
				if (genreMap.containsKey(song.getGenre().toString())) {
					ArrayList<Integer> genreIndex = genreMap.get(song.getGenre().toString());
					genreIndex.add(i);
				} else {
					ArrayList<Integer> genreIndex = new ArrayList<>();
					genreIndex.add(i);
					// while adding the genre to the map, convert it to a string
					genreMap.put(song.getGenre().toString(), genreIndex);
				}

			} else if (contents.get(i).getType().equals(AudioBook.TYPENAME)) {
				AudioBook audiobook = (AudioBook) contents.get(i);
				// If the map contains the author already then add the index of the content to the integer array list
				if (artistMap.containsKey(audiobook.getAuthor())) {
					ArrayList<Integer> integers = artistMap.get(audiobook.getAuthor());
					integers.add(i);
				} else {
					// If the map does not contain the author then create a new integer array
					// list and add it into the map
					ArrayList<Integer> integers = new ArrayList<>();
					integers.add(i);
					artistMap.put(audiobook.getAuthor(), integers);
				}
			}
		}

	}

	private ArrayList<AudioContent> getAudioContentFile() {
		try {
			contents = new ArrayList<AudioContent>();
			File newFile = new File("store.txt");
			Scanner in = new Scanner(newFile);
			// read in all the information from the store.txt file
			while (in.hasNextLine()) {
				String type = in.nextLine();
				String id = in.nextLine();
				String title = in.nextLine();
				int year = Integer.parseInt(in.nextLine());
				int length = Integer.parseInt(in.nextLine());
				String audioFile = "";

				if (type.equalsIgnoreCase("SONG")) {
					String artist = in.nextLine();
					String composer = in.nextLine();
					String genre = in.nextLine();
					String lyrics = "";
					audioFile = lyrics;
					int num = Integer.parseInt(in.nextLine());
					for (int i = 0; i < num; i++) {
						lyrics += in.nextLine() + "\n";
					}
					// convert from String genre to Song.Genre
					Song.Genre songGenre = Song.Genre.valueOf(genre);
					System.out.println("Loading SONG");
					// add the song to the contents array list by first creating a new song
					contents.add(new Song(title, year, id, Song.TYPENAME, audioFile, length, artist, composer,
							songGenre, lyrics));

				}

				else if (type.equalsIgnoreCase("AUDIOBOOK")) {
					ArrayList<String> chapterTitles = new ArrayList<String>();
					ArrayList<String> chapters = new ArrayList<String>();

					String author = in.nextLine();
					String narrator = in.nextLine();
					int numOfChapters = Integer.parseInt(in.nextLine());
					// add chapter titles to the chapterTitles array list
					for (int i = 0; i < numOfChapters; i++) {
						chapterTitles.add(in.nextLine());
					}
					// add chapter lines to the chapters array list
					for (int i = 0; i < numOfChapters; i++) {
						int numOfLines = Integer.parseInt(in.nextLine());
						String line = "";
						for (int j = 0; j < numOfLines; j++) {
							line += in.nextLine() + "\n";

						}
						chapters.add(line);
					}
					System.out.println("Loading AUDIOBOOK");
					// add the AudioBook to the contents array list by first creating a new AudioBook
					contents.add(new AudioBook(title, year, id, AudioBook.TYPENAME, audioFile, length, author, narrator,
							chapterTitles, chapters));

				}

			}

			in.close();		// close the file after reading
		// catch IOException which is a superclass of the FileNotFound exception
		} catch (IOException exception) {
			System.out.println(exception);
			System.exit(1);
		}
		return contents;
	}

	public AudioContent getContent(int index) {
		if (index < 1 || index > contents.size()) {
			return null;
		}
		return contents.get(index - 1);
	}

	public void listAll() {
		for (int i = 0; i < contents.size(); i++) {
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	public void searchTitle(String title) {
		boolean found = false;
		// iterate through the keys of the map and if it contains the title then 
		// get the index and print the information about the AudioContent
		for (String key : titleMap.keySet()) {
			if (key.contains(title)) {
				found = true;
				int index = titleMap.get(key);
				System.out.print((index + 1) + ". ");
				contents.get(index).printInfo();
				break;
			}
		} // if title is not found within the map then throw an exception
		if (!found) {
			throw new ContentNotFoundInStore("No matches for " + title);
		}
	}

	public ArrayList<Integer> searchA(String artist) {
		ArrayList<Integer> integers = new ArrayList<>();
		boolean found = false;
		// iterate through the keys of the map and if it contains the artist then 
		// get the index and print the information about the AudioContent
		for (String key : artistMap.keySet()) {
			if (key.contains(artist)) {
				found = true;
				integers = artistMap.get(key);
				return integers;
			}
		} // if artist is not found within the map then throw an exception
		if (!found) {
			throw new ContentNotFoundInStore("No matches for " + artist);
		} // return an array list of indices containing the specified artist
		return integers;
	}

	public ArrayList<Integer> searchG(String genre) {
		ArrayList<Integer> genreIndex = new ArrayList<>();
		boolean found = false;
		// iterate through the keys of the map and if it contains the genre then 
		// get the index and print the information about the AudioContent
		for (String key : genreMap.keySet()) {
			if (key.equalsIgnoreCase(genre)) {
				found = true;
				genreIndex = genreMap.get(key);
				return genreIndex;
			}
		} // if genre is not found within the map then throw an exception
		if (!found) {
			throw new ContentNotFoundInStore("No matches for " + genre);
		} // return an array list of indices containing the specified genre
		return genreIndex;
	}

	public void searchP(String target) {
		int found = 0;
		for (AudioContent content : contents) {
			// if the content is a song, call the searchSong method defined in Song.java
			// searchSong returns a boolean based on if the target string was found
			if (content.getType().equals(Song.TYPENAME)) {
				Song song = (Song) content;
				// if target string is found then increase the count of found variable
				if (song.searchSong(target)){
					found += 1;
				}
			// if the content is a audiobook, call the searchBook method defined in AudioBook.java
			// searchBook returns a boolean based on if the target string was found
			} else if (content.getType().equals(AudioBook.TYPENAME)) {
				AudioBook book = (AudioBook) content;
				// if target string is found, increase the count of the found variable
				if (book.searchBook(target)){
					found += 1;
				}
			}
		} // if the target string was not located within the content, throw an exception
		if (found == 0){
			throw new ContentNotFoundInStore("No matches for " + target);
		}
	}
	// Podcast Seasons
	 private ArrayList<Season> makeSeasons()
	 {
		ArrayList<Season> seasons = new ArrayList<Season>();
		
		Season s1 = new Season();
		s1.getEpisodesTitles().add("Bay Blanket");
		s1.getEpisodesTitles().add("You Don't Want to Sleep Here");
		s1.getEpisodesTitles().add("The Gold Rush");
		s1.getEpisodeFiles().add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		+ "lip-syncing, but some people believe they were used to spread\r\n"
		+ " smallpox and decimate entire Indigenous communities. \r\n"
		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		+ " very complicated story of the iconic striped blanket.");
		s1.getEpisodeFiles().add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"+
		"But what did the mining industry cost the original people of the territory? \r\n"+
		"And what was left when all the gold was gone? And what is a sour toe cocktail?");
		s1.getEpisodeFiles().add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"+
		"But what did the mining industry cost the original people of the territory? \r\n"+
		"And what was left when all the gold was gone? And what is a sour toe cocktail?");
		s1.getEpisodesLength().add(31);
		s1.getEpisodesLength().add(32);
		s1.getEpisodesLength().add(45);
		seasons.add(s1);
		Season s2 = new Season();
		s2.getEpisodesTitles().add("Toronto vs Everyone");
		s2.getEpisodesTitles().add("Water");
		s2.getEpisodeFiles().add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"+
		"But what did the mining industry cost the original people of the territory? \r\n"+
		"And what was left when all the gold was gone? And what is a sour toe cocktail?");
		s2.getEpisodeFiles().add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"+
		" In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"+
		" and vampires, and discuss some big concerns currently facing Canada's water.");
		s2.getEpisodesLength().add(45);
		s2.getEpisodesLength().add(50);
		
		seasons.add(s2);
		return seasons;
	 }
	 
}
