/*
 * Name: Phuoc Hai Le
 * Student ID: 501203981
 */
import java.util.ArrayList;
public class Podcast extends AudioContent {
    public static final String TYPENAME =	"PODCAST";
    


    private String host;
	private int currentEpisode =1;
	private int currentSeason = 1;
    private ArrayList<Season> seasons;


    public Podcast(String title, int year, String id, String type, String audioFile, int length,
                    String host, ArrayList<Season> seasons ){

        super(title, year, id, type, audioFile, length);
        this.host = host;

        this.seasons = seasons;

    }

    public String getType()
	{
		return TYPENAME;
	}

    public void printInfo()
	{
		super.printInfo();
		System.out.println("Host: "+ host );
        System.out.println("Seasons: "+ seasons.size());
	}

    public void play()
	{
		super.setAudioFile(this.seasons.get(currentSeason-1).episodeTitles.get(currentEpisode-1) + "\n"+this.seasons.get(currentSeason-1).episodeFiles.get(currentEpisode-1));
		super.play();
		//System.out.println(getEpisodes().get(currentEpisode));
	}

	public void setCurrentSeason(int num){
		if (num>0 && num <= seasons.size()){
			currentSeason = num;
		}
	}

	public void setCurrentEpisode(int num){
		if (num>0 && num <= seasons.get(currentSeason-1).episodeTitles.size()){
			currentEpisode = num;
		}
	}

    public void printTOC()
	{
		for(int i = 0; i<seasons.size();i++){
			System.out.println("Season "+(i+1) +". "+seasons.get(i));
		}
	}


    public boolean equals(Object other)
	{
		Podcast otherCon = (Podcast) other;
		return super.equals(other) && host.equals(otherCon.host);
	}


	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}



	public ArrayList<Season> getSeasons()
	{
		return seasons;
	}

	public void setSeasons(ArrayList<Season> seasons)
	{
		this.seasons = seasons;
	}



}
