package search;
import twitter4j.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class searchTest {
	
	public static void main(String[] args)
	{
		String sFileName = "C:\\Users\\Hannah\\Documents\\Big Data\\tweets.csv";		
		
		twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
		cb.setOAuthConsumerKey("vjg0oB74gT9rk1Uvvq2YA");
		cb.setOAuthConsumerSecret("GyY5TwvBPdorfuutN2vUL3A3iVdAsRKGWN7HXEKrEBg");
		cb.setOAuthAccessToken("368997455-CeNaxloIHefYsxtBJjo7OVZOoHzLTpHlzh9AI9U0");
		cb.setOAuthAccessTokenSecret("X3SD7JSdbDXQCTIoSQUBv5421fxaKUiVHic8ATOOigfn4");
		
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();	
		
		try
		{
			FileWriter fw = new FileWriter(sFileName);

			PrintWriter pw = new PrintWriter(fw);
			
			pw.print("Created Date");
			pw.print(",");
			pw.print("User Screen Name");
			pw.print(",");
			pw.print("User Name");
			pw.print(",");
			pw.print("User Location");
			pw.print(",");
			pw.print("Tweet");
			pw.print(",");
			pw.print("Retweet Count");
			pw.print(",");
			pw.print("Follower Count");
			pw.print(",");
			pw.print("Friends Count");
			pw.print(",");
			pw.println("Hashtags");
			//pw.print(",");
			//pw.println("GeoLocation");
			
			Query query = new Query("education");
			
			QueryResult result;
			result = twitter.search(query);
				
				List<Status> tweets = result.getTweets();
				Status tweet = tweets.get(0);
				
				tweet.getText().replaceAll("\\n", "");
				
				
					pw.print(tweet.getCreatedAt().toString());
					pw.print(",");
					pw.print("@" + tweet.getUser().getScreenName());
					pw.print(",");
					pw.print(tweet.getUser().getName());
					pw.print(",");
					pw.print("\"" + tweet.getUser().getLocation() + "\"");
					pw.print(",");
					pw.print("\"" + tweet.getText() + "\"");
					pw.print(",");
					pw.print(String.valueOf(tweet.getRetweetCount()));
					pw.print(",");
					pw.print(String.valueOf(tweet.getUser().getFollowersCount()));
					pw.print(",");
					pw.print(String.valueOf(tweet.getUser().getFriendsCount()));
					pw.print(",");
					pw.println("\"" + (tweet.getHashtagEntities().toString()) + "\"");
					//pw.print(",");
					//pw.println(tweet.getGeoLocation().toString());
			
			pw.flush();
			pw.close();
			fw.close();
			System.exit(0);
		}
		catch(IOException e){
			e.getMessage();
		}
		catch(TwitterException e){
			e.printStackTrace();
			System.out.println("Failed to search tweets:" + e.getMessage());
			System.exit(-1);
		}
	}
}
