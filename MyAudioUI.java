/*
 * Name: Phuoc Hai Le
 * Student ID: 501203981
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.*;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int fromIndex = 0;
				int toIndex = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					fromIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					toIndex = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}


				for(int i = fromIndex; i < toIndex+1; i++){
					AudioContent content = store.getContent(i);
					if (content == null)
						System.out.println("Content Not Found in Store");
					else {
						try{
							mylibrary.download(content);
							System.out.println(content.getType()+" "+content.getTitle()+" Added to Library");
						}catch (ActionDoneException e){
							System.out.println(e.getMessage());
						}
					}

				}
									
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				int index = 0;
				
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{	
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Song Not Found");
				else {
					try{
						mylibrary.playSong(index);
					}catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
				
			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				int index = 0;

				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Audiobook Not Found");
				else {
					try{
						mylibrary.printAudioBookTOC(index);
					}catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int number = 0, chapter = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					number = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(number);
				if (content == null)
					System.out.println("Audiobook Not Found");
				else 
				{
					System.out.print("Chapter: ");
					if (scanner.hasNextInt())
					{
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					AudioContent content2 = store.getContent(chapter);
				
					if (content2 == null)
						System.out.println("Chapter Not Found");
					else {
						try{
							mylibrary.playAudioBook(number, chapter);
						}catch(AudioContentNotFoundException e){
							System.out.println(e.getMessage());
						}catch (ObjectNotFoundException e){
							System.out.println(e.getMessage());
						}
					}
				}
				
				
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) //Podcast out of scope
			{
				int number = 0, index =0;
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					number = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(number);
				if (content == null)
					System.out.println("Podcast Not Found");
				else {
					System.out.print("Season: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					AudioContent content2 = store.getContent(index);
					if (content2 == null)
						System.out.println("Season Not Found");
					else if (!mylibrary.printPodTOC(number, index)){
						System.out.println(mylibrary.getErrorMessage());
					}
				}
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) //Podcast out of scope
			{
				int number =0, number2 = 0, number3 = 0;
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					number = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(number);
				if (content == null){
					System.out.println("Podcast Not Found");
				}else {
					System.out.print("Season: ");
					if (scanner.hasNextInt()){
						number2 = scanner.nextInt();
						scanner.nextLine();
					}
					AudioContent content2 = store.getContent(number2);
					if (content2 == null){
						System.out.println("Season Not Found");
					}else
					{
						System.out.print("Episode: ");
						if (scanner.hasNextInt()){
							number3 = scanner.nextInt();
							scanner.nextLine();
						}
						AudioContent content3 = store.getContent(number3);
						if (content3 == null)
							System.out.println("Episode Not Found");
						else if (!mylibrary.playPodcast(number, number2, number3)){
							System.out.println(mylibrary.getErrorMessage());
						}
					}
				}
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				try{
					mylibrary.playPlaylist(name);
				}catch(ObjectNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				int index =0;
				System.out.print("Content Number: ");
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null){
					System.out.println("Content Not Found");
				}
				else {
					try{
						mylibrary.playPlaylist(name,index);
					}catch (AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}catch (ObjectNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index =0;
				System.out.print("Library Song #: ");
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Song Not Found");
				else {
					try{
						mylibrary.deleteSong(index);
					}catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				try{
					mylibrary.makePlaylist(name);
				}catch (ObjectAlreadyExistException e){
					System.out.println(e.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				try{
					mylibrary.printPlaylist(name);
				}catch(ObjectNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				System.out.print("Content Type [SONG, AUDIOBOOK, PODCAST]: ");
				String type = scanner.nextLine();
				System.out.print("Library Content #: ");
				
				int index = 0;
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Content Not Found");
				else {
					try{
						mylibrary.addContentToPlaylist(type, index, name);
					}catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}catch (ActionDoneException e){
						System.out.println(e.getMessage());
					}catch (ObjectNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				System.out.print("Playlist Title: ");
				String name = scanner.nextLine();
				System.out.print("Playlist Content #: ");
				int index =0;
				if (scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Content Not Found");
				else {
					try{
						mylibrary.delContentFromPlaylist(index, name);
					}catch(IndexOutOfBoundsException e){
						System.out.println(e.getMessage());
					}catch(ObjectNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}
			else if (action.equalsIgnoreCase("SEARCH")){
				System.out.print("Title: ");
				String title ="";
				if(scanner.hasNextLine()){
					title = scanner.nextLine();
				}
				if(!store.search(title)){
					System.out.println("No matches for "+ title);
				}
			}
			else if (action.equalsIgnoreCase("SEARCHA")){
				System.out.print("Artist: ");
				String name ="";
				if(scanner.hasNextLine()){
					name = scanner.nextLine();
				}
				if(!store.searchA(name)){
					System.out.println("No matches for "+ name);
				}
			}
			else if (action.equalsIgnoreCase("SEARCHG")){
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				String genre ="";
				if(scanner.hasNextLine()){
					genre = scanner.nextLine();
				}
				if(!store.searchG(genre)){
					System.out.println("No matches for "+ genre);
				}
			}else if (action.equalsIgnoreCase("DOWNLOADA")){
				System.out.print("Artist Name: ");
				String name = "";
				name = scanner.nextLine();
				for(AudioContent i : store.downloadA(name)){
					try{
						mylibrary.download(i); // I make use of the method download 
					}catch (ActionDoneException e){
						System.out.println(e.getMessage());
					}
				}
				

		
				
			}else if (action.equalsIgnoreCase("DOWNLOADG")){
				System.out.print("Genre: ");
				String genre = "";
				genre = scanner.nextLine();
				
				for(AudioContent i : store.downloadG(genre)){
					try{
						mylibrary.download(i);
					}catch (ActionDoneException e){
						System.out.println(e.getMessage());
					}
				}
				
			
			}

			System.out.print("\n>");
		}
		
	}
	
}
