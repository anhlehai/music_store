/*
 * Name: Phuoc Hai Le
 * Student ID: 501203981
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  	private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  	podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		
			if (content.getType().equals(Song.TYPENAME)){
				for (int i = 0; i< songs.size(); i++){
					if (content.equals(songs.get(i))){
						throw new ActionDoneException("Song '"+ content.getTitle()+"' already downloaded");
					} 
					
				}
				songs.add((Song) content);
				
			}
		
		
		
			if (content.getType().equals(AudioBook.TYPENAME)){
				for (int i = 0; i<audiobooks.size();i++){
					if (content.equals(audiobooks.get(i))){
						throw new ActionDoneException("Audiobook '"+ content.getTitle()+"' already downloaded");
					}
				}
				audiobooks.add((AudioBook) content);
				
			}
		
		// if (content.getType().equals(Podcast.TYPENAME)){
		// 	for (int i = 0; i<podcasts.size();i++){
		// 		if (content.equals(podcasts.get(i))){
		// 			errorMsg = "Podcast already downloaded";
		// 			return false;
		// 		}
		// 	}
		// 	podcasts.add((Podcast) content);
		// 	return true;
		// }
		
		// return true;
	}
	
	

	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		for (int i = 0; i < podcasts.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			podcasts.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			
			System.out.println(playlists.get(i).getTitle());	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> emp = new ArrayList<>();
		boolean empty = true;
		boolean already = false;
		for (int i = 0; i<songs.size(); i++){
			
			if (empty){
				emp.add(songs.get(i).getArtist());
				empty = false;
				
			}
			//check if the name already there
			for (int j =0; j<emp.size(); j++){
				if ( songs.get(i).getArtist().equals(emp.get(j))){
					already = true;
					break;
				}else{
					already = false;
				}
			}
			if (!already){
				emp.add(songs.get(i).getArtist());
			}
		} 
		for (int i = 0; i < emp.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(emp.get(i));	
		}
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		
			if(index <1 || index > songs.size()){
				throw new AudioContentNotFoundException("Song Not Found");
			}
			for(int i = 0 ; i < playlists.size(); i++){
				for (int j = 0; j < playlists.get(i).getContent().size(); j++){
					//this block of code is used to delete song from a playlist
					//so the song will be compared to each content in the playlist
					//error will happen if class:song compares to class:book in playlist; therefore,
					//the code below compares only contents with the same type
					if(playlists.get(i).getContent().get(j).getType().equals(Song.TYPENAME) && songs.get(index-1).equals(playlists.get(i).getContent().get(j))){
						playlists.get(i).getContent().remove(j);
					}
				}
			}
			songs.remove(index-1);
			
			
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs,new SongYearComparator());

	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			return a.getYear()-b.getYear();
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			return a.getLength()- b.getLength();
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		
			if (index < 1 || index > songs.size())
			{
				throw new AudioContentNotFoundException("Song Not Found");
			}
			songs.get(index-1).play();
			
		
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		if (index < 1 || index > podcasts.size())
		{
			errorMsg = "Podcast Not Found";
			return false;
		}
		if (season < 1 || season > podcasts.get(index-1).getSeasons().size())
		{
			errorMsg = "Season Not Found";
			return false;
		}
		if (episode < 1 || episode > podcasts.get(index-1).getSeasons().get(season-1).episodeTitles.size())
		{
			errorMsg = "Episode Not Found";
			return false;
		}
		// podcasts.get(index-1).printInfo();
		// System.out.println();
		podcasts.get(index-1).setCurrentSeason(season);
		podcasts.get(index-1).setCurrentEpisode(episode);
		podcasts.get(index-1).play();
		// System.out.println(podcasts.get(index-1).getSeasons().get(season-1).episodeTitles.get(episode-1));
		// System.out.println(podcasts.get(index-1).getSeasons().get(season-1).episodeFiles.get(episode-1));
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	//this is printPodTOC below
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		
			if (index < 1 || index > audiobooks.size())
			{
				throw new AudioContentNotFoundException("Audiobok Not Found");
			}
			
			if (chapter < 1 || chapter > audiobooks.get(index-1).getNumberOfChapters())
			{
				throw new ObjectNotFoundException("Chapter Not Found");
			}
			audiobooks.get(index-1).selectChapter(chapter);
			audiobooks.get(index-1).play();
			
			
			
		
		
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		
			if (index < 1 || index > audiobooks.size())
			{
				throw new AudioContentNotFoundException("Audiobook Not Found");
			}
			audiobooks.get(index-1).printTOC();
			
		
	}

	//Podcast is out of scope for assignment 2
	public boolean printPodTOC(int number, int index){
		
		if (number >0 && number <= podcasts.size()){	
			if (index<1 || index >podcasts.get(number-1).getSeasons().size() ){
				errorMsg = "Season Not Found";
				return false;
			}
			for (int j = 0; j < podcasts.get(number-1).getSeasons().get(index-1).episodeTitles.size(); j++){
					System.out.print("Episode "+ (j+1) + ". ");
					System.out.println(podcasts.get(number-1).getSeasons().get(index-1).episodeTitles.get(j));
				}//basically printing out the episode titles
				return true;
			
		}
		errorMsg = "Podcast Not Found";
		return false;
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		
			for (int i =0; i< playlists.size(); i++){
				if (title.equals(playlists.get(i).getTitle())){
					throw new ObjectAlreadyExistException("Playlist '"+title+"' already exist");
				}
			}
			playlists.add(new Playlist(title));
			
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		
			for (int i = 0; i < playlists.size();i++){
				if (!title.equals(playlists.get(i).getTitle())){
					
					throw new ObjectNotFoundException("Playlist '"+title+"' Not Found");
					
				}else{
					playlists.get(i).printContents();
				}
				
			}
			
			
		
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		
			for(int i= 0; i < playlists.size(); i++){
				if (!playlistTitle.equals(playlists.get(i).getTitle())){
					
					throw new ObjectNotFoundException("Playlist '"+playlistTitle+"' Not Found");
				}else
					playlists.get(i).playAll();
			}
			
		
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		
			for(int i= 0; i < playlists.size(); i++){
				if (playlistTitle.equals(playlists.get(i).getTitle())){
					if (indexInPL>0 && indexInPL <= playlists.get(i).getContent().size()){
						playlists.get(i).play(indexInPL);
						
					}else
						throw new AudioContentNotFoundException("Content Not Found");
				}else
					throw new ObjectNotFoundException("Playlist '"+playlistTitle+"' Not Found");
				
			}
			
		
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		
			for(int i = 0; i<playlists.size(); i++){
				if (playlistTitle.equals(playlists.get(i).getTitle())){
					if(type.equalsIgnoreCase(Song.TYPENAME)){
						if (index<1 || index>songs.size()){
							throw new AudioContentNotFoundException("Song Not Found");
						}
						for(int j = 0; j < playlists.get(i).getContent().size(); j++){
							if (playlists.get(i).getContent().get(j).getType().equals(Song.TYPENAME) && songs.get(index-1).equals(playlists.get(i).getContent().get(j))){
								throw new ActionDoneException("Song already in playlist");
							}//similar to deletesong method, comparing objects of the same type
						}
						playlists.get(i).addContent(songs.get(index-1));
						
					}
					else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){
						if (index<1 || index>audiobooks.size()){
							throw new AudioContentNotFoundException("Book Not Found");
						}
						for(int j = 0; j < playlists.get(i).getContent().size(); j++){
							if (playlists.get(i).getContent().get(j).getType().equals(AudioBook.TYPENAME) &&audiobooks.get(index-1).equals(playlists.get(i).getContent().get(j))){
								throw new ActionDoneException("Book already in playlist");
							}
						}
						playlists.get(i).addContent(audiobooks.get(index-1));
						
					}
					else if(type.equalsIgnoreCase(Podcast.TYPENAME)){
						if (index<1 || index>podcasts.size()){
							throw new AudioContentNotFoundException("Podcast Not Found");
						}
						for(int j = 0; j < playlists.get(i).getContent().size(); j++){
							if (playlists.get(i).getContent().get(j).getType().equals(Podcast.TYPENAME) &&podcasts.get(index-1).equals(playlists.get(i).getContent().get(j))){
								throw new ActionDoneException("Podcast already in playlist");
							}
						}
						playlists.get(i).addContent(podcasts.get(index-1));
						
					}else
						throw new ObjectNotFoundException("Type Not Found");
				}else
					throw new ObjectNotFoundException("Playlist Not Found");
			}
			
		
	
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		
			for(int i = 0 ; i < playlists.size(); i++){
				if (title.equals(playlists.get(i).getTitle())){
					if(index<1 || index > playlists.get(i).getContent().size()){
						throw new IndexOutOfBoundsException("Index Out of Bound");
					}
					playlists.get(i).deleteContent(index);;
					
				}else
					throw new ObjectNotFoundException("Playlist Not Found");
				
			}
			
		
	}
	

	
}

class AudioContentNotFoundException extends RuntimeException
{
	public AudioContentNotFoundException(){}

	public AudioContentNotFoundException(String message){
		super(message);
	}
}

class ActionDoneException extends RuntimeException
{
	public ActionDoneException(){}

	public ActionDoneException(String message){
		super(message);
	}
}

class ObjectNotFoundException extends RuntimeException
{
	public ObjectNotFoundException(){}

	public ObjectNotFoundException(String message){
		super(message);
	}
}

class ObjectAlreadyExistException extends RuntimeException
{
	public ObjectAlreadyExistException(){}

	public ObjectAlreadyExistException(String message){
		super(message);
	}
}

