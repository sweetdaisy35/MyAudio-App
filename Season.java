/*
 * Name: Suboohi Sayeed
 * Student ID: 501175964
 */
import java.util.ArrayList;

/*
 *  This class holds the episode information for a Season
 */
public class Season {
    // initialize array lists based on the episodes
    public ArrayList<String> episodeFiles = new ArrayList<String>();    // empty list for the episode strings
    private ArrayList<String> episodeTitles = new ArrayList<String>();  // empty list for the episode titles 
    private ArrayList<Integer> episodeLength = new  ArrayList<Integer>();   // empty list for the episode lengths

    // Contructor of the Season class - no need to initialize any instance variables
    public Season (){
    }

    // return episodeFiles array list 
    public ArrayList<String> getEpisodeFiles() {
        return episodeFiles;
    }
    // return episodeTitles array list
    public ArrayList<String> getEpisodesTitles() {
        return episodeTitles;
    }

    // return episodeLength array list
    public ArrayList<Integer> getEpisodesLength() {
        return episodeLength;
    }

}
