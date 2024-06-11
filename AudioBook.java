
/*
 * Name: Suboohi Sayeed
 */
import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent {
	public static final String TYPENAME = "AUDIOBOOK";

	private String author;
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
			String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters) {
		// Make use of the constructor in the super class AudioContent.
		super(title, year, id, type, audioFile, length);
		// Initialize additional AudioBook instance variables.
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = chapterTitles;
		this.chapters = chapters;
	}

	// getter method for the type of AudioContent
	public String getType() {
		return TYPENAME;
	}

	// Print information about the audiobook. First print the basic information of
	// the AudioContent
	// by making use of the printInfo() method in superclass AudioContent and then
	// print author and narrator
	// see the video
	public void printInfo() {
		super.printInfo();
		System.out.println("Author: " + author + " Narrated by: " + narrator);
	}

	// Play the audiobook by setting the audioFile to the current chapter title
	// (from chapterTitles array list)
	// followed by the current chapter (from chapters array list)
	// Then make use of the the play() method of the superclass
	public void play() { // get the chapter title and the current chapter using the index currentChapter
		String file = chapterTitles.get(currentChapter) + ".\n" + chapters.get(currentChapter);
		// set the audio file to the string containing the current chapter
		super.setAudioFile(file);
		// call the play method of Audiocontent that prints the audio file
		super.play();

	}

	// Print the table of contents of the book - i.e. the list of chapter titles
	// See the video
	public void printTOC() {
		// iterate through the chapter titles array list to print out each title
		for (int title = 0; title < chapterTitles.size(); title++) {
			System.out.println("Chapter " + (title + 1) + ". " + chapterTitles.get(title));
			System.out.println();
		}

	}

	// Select a specific chapter to play - nothing to do here
	public void selectChapter(int chapter) {
		if (chapter >= 1 && chapter <= chapters.size()) {
			currentChapter = chapter - 1;
		} else {
			throw new ChapterNotFound("Chapter Does Not Exist");
		}
	}

	// Two AudioBooks are equal if their AudioContent information is equal and both
	// the author and narrators are equal
	public boolean equals(Object other) {
		// cast the other object to an object of this class
		AudioBook otherBook = (AudioBook) other;
		// return true if the title, id, author and narrator are of the two Audiobooks
		// are equal
		if (super.equals(otherBook) && author.equals(otherBook.author) && narrator.equals(otherBook.narrator)) {
			return true;
		}
		return false;
	}

	/*
	 * String title, int year, String id, String type, String audioFile, int length,
	 * String author, String narrator, ArrayList<String> chapterTitles,
	 * ArrayList<String> chapters
	 */
	public boolean searchBook(String target) {
		boolean found = false;
		String chaptersStr = "";
		String titles = "";
		for (int i = 0; i < chapters.size(); i++) {
			chaptersStr += chapters.get(i);
		}
		for (int i = 0; i < chapterTitles.size(); i++) {
			titles += chapterTitles.get(i);
		}
		if (author.contains(target) || narrator.contains(target) || getTitle().contains(target)
				|| getAudioFile().contains(target) || getType().contains(target) || getId().contains(target)
				|| Integer.toString(getLength()).contains(target) || Integer.toString(getYear()).contains(target)
				|| chaptersStr.contains(target) || titles.contains(target)) {
			found = true;
		}
		if (found) {
			System.out.println("Found in: ");
			printInfo();
		}
		return found;
	}

	public int getNumberOfChapters() {
		return chapters.size();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNarrator() {
		return narrator;
	}

	public void setNarrator(String narrator) {
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles() {
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles) {
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters() {
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters) {
		this.chapters = chapters;
	}

}
