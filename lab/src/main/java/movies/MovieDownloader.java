package movies;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * A class for downloading movie data from the internet.
 * Code adapted from Google.
 *
 * YOUR TASK: Add comments explaining how this code works!
 * 
 * @author Joel Ross & Kyungmin Lee
 */
public class MovieDownloader {

	// returns a String array
	public static String[] downloadMovieData(String movie) {

		// construct the url for the omdbapi API
		String urlString = "http://www.omdbapi.com/?s=" + movie + "&type=movie";
		
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;

		String movies[] = null;

		// if code inside try block throws an error, catch block gets executed
		try {
			// creates a new URL object from urlString
			URL url = new URL(urlString);

			// sets urlConnection to Http casted connection to urlString
			urlConnection = (HttpURLConnection) url.openConnection();
			// sets ajax request to type get
			urlConnection.setRequestMethod("GET");
			// connects to url
			urlConnection.connect();

			// instantiates InputStream object to read stream coming from url
			InputStream inputStream = urlConnection.getInputStream();
			// a string that can be used across multiple threads
			StringBuffer buffer = new StringBuffer();
			if (inputStream == null) {
				return null;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));

			// build up buffer, line by line, until reader.readLine() has been traversed
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line + "\n");
			}

			// if after building up buffer, if nothing was added, return null
			if (buffer.length() == 0) {
				return null;
			}

			// Produce string, remove extraneous characters
			String results = buffer.toString();
			results = results.replace("{\"Search\":[","");
			results = results.replace("]}","");
			results = results.replace("},", "},\n");

			movies = results.split("\n");
		}
		// if IOException is thrown, return null
		catch (IOException e) {
			return null;
		}
		// disconnect urlConnection, close BufferReader
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} 
				catch (final IOException e) {
				}
			}
		}

		// return movies string
		return movies;
	}

	// get user input and send it to downloadMovieData method
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter a movie name to search for: ");
		String searchTerm = sc.nextLine().trim();
		String[] movies = downloadMovieData(searchTerm);
		for(String movie : movies) {
			System.out.println(movie);
		}
		
		sc.close();
	}
}
