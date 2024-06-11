/*
 * Name: Suboohi Sayeed
 */
import java.util.ArrayList;

/*
 * A Podcast is a type of AudioContent. A Podcast has extra fields such as Host and Season Number.
 */
public class Podcast extends AudioContent {

    public static final String TYPENAME =	"PODCAST";

    private ArrayList<Season> seasons;
    private String host;
    private int seasonNumber;
    private int currentSeason;
    private int currentEpisode;

	public Podcast (String title, int year, String id, String type, String audioFile, int length, String host, ArrayList<Season> seasons, int seasonNumber){
        // Make use of the constructor in the super class AudioContent. 
        super(title, year, id, type, audioFile, length);
        // Initialize additional AudioBook instance variables. 
        this.host = host;
        this.seasons = seasons;
        this.seasonNumber = seasonNumber;

    }
    // getter method for retrieving the TYPENAME Podcast
    public String getType()
	{
		return TYPENAME;
	}

    // Play the podcast by setting the audioFile to the current season (from seasons array list) 
	// followed by the current episode (from episodeFiles array list)
	// Then make use of the the play() method of the superclass
    public void play()
	{   // get the current season at the index (currentSeason)
        Season season = seasons.get(currentSeason);
        // get the current episode title and the current episode file using the index currentEpisode
        String file = season.getEpisodesTitles().get(currentEpisode) + ".\n" + season.getEpisodeFiles().get(currentEpisode);
        this.setAudioFile(file);
		super.play();
		
	}
    // Print the table of contents of the podcast - i.e. the list of episode titles
    public void printTOC()
	{
        for (int title = 0; title < seasons.get(currentSeason).getEpisodesTitles().size(); title ++){
            // get the episode titles of the current season using the index (title)
            System.out.println("Episode " + (title + 1) + ". " + seasons.get(currentSeason).getEpisodesTitles().get(title));
			System.out.println();
        }
		
	}

    // Print information about the podcast. First print the basic information of the Podcast 
	// by making use of the printInfo() method in superclass AudioContent and then print host and number of seasons
    public void printInfo()
	{
		super.printInfo();
		System.out.println("Host: " + host + "\nSeasons: " + seasonNumber);
	}

    // get the seasons array list
    public ArrayList<Season> getSeasons() {
        return seasons;
    }
    // set the current season number to the given index parameter (seasonIndex)
    public void setSeason(int seasonIndex){
        this.currentSeason = seasonIndex;
    }

    // set the current episode number to the given index parameter (episode)
    public void setEpisode(int episode){
        this.currentEpisode = episode;
    }
}
