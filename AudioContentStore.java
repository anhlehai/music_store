/*
 * Name: Phuoc Hai Le
 * Student ID: 501203981
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.*;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		
		public AudioContentStore()
		{
			contents = new ArrayList<AudioContent>();
			try {
				contents = createStore();
			} catch (IOException e){
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
		  

		}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		

		public boolean search(String title) {
			Map<String, Integer> storeMap = new HashMap<String, Integer>();

			for (int i = 0; i < contents.size(); i++){
				storeMap.put(contents.get(i).getTitle(), i+1);
			}
			if (storeMap.containsKey(title)){
				int index = storeMap.get(title);
				System.out.print(index+". ");
				contents.get(index-1).printInfo();
				return true;
			}
			return false;
			

		
		}
		public Map<String, ArrayList<Integer>> createAMap(){
			Map<String, ArrayList<Integer>> AMap = new HashMap<String, ArrayList<Integer>>();
			for (int i = 0; i < contents.size(); i++){
				if (contents.get(i).getType().equals(Song.TYPENAME)){
					Song temp = (Song) contents.get(i);
					if(AMap.containsKey(temp.getArtist())){
						ArrayList<Integer> indexList =  AMap.get(temp.getArtist());
						indexList.add(i+1);
					}else{
						AMap.put(temp.getArtist(),new ArrayList<Integer>());
						AMap.get(temp.getArtist()).add(i+1);
					}
				}else if (contents.get(i).getType().equals(AudioBook.TYPENAME)){
					AudioBook temp = (AudioBook) contents.get(i);
					if(AMap.containsKey(temp.getAuthor())){
						ArrayList<Integer> indexList =  AMap.get(temp.getAuthor());
						indexList.add(i+1);
					}else{
						AMap.put(temp.getAuthor(), new ArrayList<>());
						AMap.get(temp.getAuthor()).add(i+1);
					}
				}else if (contents.get(i).getType().equals(Podcast.TYPENAME)){
					Podcast temp = (Podcast) contents.get(i);
					if(AMap.containsKey(temp.getHost())){
						ArrayList<Integer> indexList =  AMap.get(temp.getHost());
						indexList.add(i+1);
					}else{
						AMap.put(temp.getHost(), new ArrayList<>());
						AMap.get(temp.getHost()).add(i+1);
					}
				}
				
			}
			return AMap;
		}

		public boolean searchA(String name){
			Map<String, ArrayList<Integer>> AMap = createAMap();
			

			for(String Aname : AMap.keySet()){
				if (Aname.contains(name)){
					for(int i = 0; i < AMap.get(Aname).size(); i++){
						System.out.print(AMap.get(Aname).get(i)+". ");
						contents.get(AMap.get(Aname).get(i)-1).printInfo();
						System.out.println();
						}
						return true;
				}
			}
			
				
			
			return false;
		}
		public Map<Song.Genre, ArrayList<Integer>> createGenreMap(){
			Map<Song.Genre, ArrayList<Integer>> genreMap = new HashMap<Song.Genre, ArrayList<Integer>>();
			for (int i = 0; i < contents.size(); i++){
				if (contents.get(i).getType().equals(Song.TYPENAME)){
					Song temp = (Song) contents.get(i);
					if(genreMap.containsKey(temp.getGenre())){
						ArrayList<Integer> indexList =  genreMap.get(temp.getGenre());
						indexList.add(i+1);
					}else{
						genreMap.put(temp.getGenre(),new ArrayList<Integer>());
						genreMap.get(temp.getGenre()).add(i+1);
					}
				}
			}
			return genreMap;
		}

		public boolean searchG(String genre){
			Map<Song.Genre, ArrayList<Integer>> genreMap = createGenreMap();
			
	
			Song.Genre sGenre = Song.Genre.POP;
			if(genre.equalsIgnoreCase("POP")){
				sGenre = Song.Genre.POP;
			}else if(genre.equalsIgnoreCase("ROCK")){
				sGenre = Song.Genre.ROCK;
			}else if(genre.equalsIgnoreCase("JAZZ")){
				sGenre = Song.Genre.JAZZ;
			}else if(genre.equalsIgnoreCase("HIPHOP")){
				sGenre = Song.Genre.HIPHOP;
			}else if(genre.equalsIgnoreCase("RAP")){
				sGenre = Song.Genre.RAP;
			}else if(genre.equalsIgnoreCase("CLASSICAL")){
				sGenre = Song.Genre.CLASSICAL;
			}else {
				return false;
			}
			

			if(genreMap.containsKey(sGenre)){
				for(int i = 0; i < genreMap.get(sGenre).size(); i++){
				System.out.print(genreMap.get(sGenre).get(i)+". ");
				contents.get(genreMap.get(sGenre).get(i)-1).printInfo();
				System.out.println();
				}
				return true;	
			
			}
			return false;
		}

		private ArrayList<AudioContent> createStore() throws IOException{
			ArrayList<AudioContent> store = new ArrayList<>();
			Scanner scan = new Scanner(new File("store.txt"));
				while(scan.hasNextLine()){
					String type = scan.nextLine();
					if(type.equals("SONG")){
						String id = scan.nextLine();
						String title = scan.nextLine();
						int year = Integer.parseInt(scan.nextLine());
						int length = Integer.parseInt(scan.nextLine());
						String artist = scan.nextLine();
						String composer = scan.nextLine();
						//System.out.println(title);
						String genre = scan.nextLine();
						Song.Genre sGenre = Song.Genre.CLASSICAL;
						if(genre.equalsIgnoreCase("POP")){
							sGenre = Song.Genre.POP;
						}else if(genre.equalsIgnoreCase("ROCK")){
							sGenre = Song.Genre.ROCK;
						}else if(genre.equalsIgnoreCase("JAZZ")){
							sGenre = Song.Genre.JAZZ;
						}else if(genre.equalsIgnoreCase("HIPHOP")){
							sGenre = Song.Genre.HIPHOP;
						}else if(genre.equalsIgnoreCase("RAP")){
							sGenre = Song.Genre.RAP;
						}else if(genre.equalsIgnoreCase("CLASSICAL")){
							sGenre = Song.Genre.CLASSICAL;
						}

						int lineNums = Integer.parseInt(scan.nextLine());
						String file ="";
						for(int i = 0; i< lineNums; i++){
							file = scan.nextLine()+"\r\n";
						}

						store.add(new Song(title, year, id, type, file, length, artist, composer, sGenre, file));
						System.out.println("Loading "+type);
					}
					if (type.equals("AUDIOBOOK")){
						String id = scan.nextLine();
						String title = scan.nextLine();
						int year = Integer.parseInt(scan.nextLine());
						int length = Integer.parseInt(scan.nextLine());
						String author = scan.nextLine();
						String narrator = scan.nextLine();

						int chapTitleNums = Integer.parseInt(scan.nextLine());
						ArrayList<String> chapterTitles = new ArrayList<>();
						for(int i = 0; i< chapTitleNums; i++){
							chapterTitles.add(scan.nextLine());
						}

						int chapNums = Integer.parseInt(scan.nextLine());
						ArrayList<String> chapters = new ArrayList<>();
						for(int i = 0; i< chapTitleNums; i++){
							chapters.add("");
							String temp = "";
							for(int j = 0; j< chapNums-1; j++){
								temp = chapters.get(i);
								temp += scan.nextLine() + "\r\n";
							}
							temp += scan.nextLine();
							chapters.set(i, temp);
						}
					

						store.add(new AudioBook(title, year, id, type, title, length, author, narrator, chapterTitles, chapters));
						System.out.println("Loading "+type);
					}

				}
				return store;
		}

		public ArrayList<AudioContent> getStoreContents (){
			return contents;
		}

		public ArrayList<AudioContent> downloadA(String name){
		
			Map<String, ArrayList<Integer>> AMap = new HashMap<String, ArrayList<Integer>>();
			AMap = this.createAMap();
			ArrayList<AudioContent> AList = new ArrayList<>();	
				for(String Aname : AMap.keySet()){
					if (Aname.contains(name)){
						for(int i = 0; i < AMap.get(Aname).size(); i++){
							
							AList.add(this.getContent(AMap.get(Aname).get(i)));
							
					}
				}
			}
			return AList;
		}

		public ArrayList<AudioContent> downloadG(String genre){
			ArrayList<AudioContent> genreList = new ArrayList<>();
			Map<Song.Genre, ArrayList<Integer>> genreMap = this.createGenreMap();
				Song.Genre sGenre = Song.Genre.CLASSICAL;
						if(genre.equalsIgnoreCase("POP")){
							sGenre = Song.Genre.POP;
						}else if(genre.equalsIgnoreCase("ROCK")){
							sGenre = Song.Genre.ROCK;
						}else if(genre.equalsIgnoreCase("JAZZ")){
							sGenre = Song.Genre.JAZZ;
						}else if(genre.equalsIgnoreCase("HIPHOP")){
							sGenre = Song.Genre.HIPHOP;
						}else if(genre.equalsIgnoreCase("RAP")){
							sGenre = Song.Genre.RAP;
						}else if(genre.equalsIgnoreCase("CLASSICAL")){
							sGenre = Song.Genre.CLASSICAL;
						}

						if(genreMap.containsKey(sGenre)){
							for(int i = 0; i < genreMap.get(sGenre).size(); i++){
								
									genreList.add(this.getContent(genreMap.get(sGenre).get(i)));
								
							}
						}
			return genreList;
		}
		
}
