package search;
import twitter4j.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class searchTweets {
	
	public static void main(String[] args)
	{
		String sFileName = "C:\\Users\\Hannah\\Documents\\Big Data\\tweets-earthquake.csv";		
		
		twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
		cb.setOAuthConsumerKey("vjg0oB74gT9rk1Uvvq2YA");
		cb.setOAuthConsumerSecret("GyY5TwvBPdorfuutN2vUL3A3iVdAsRKGWN7HXEKrEBg");
		cb.setOAuthAccessToken("368997455-CeNaxloIHefYsxtBJjo7OVZOoHzLTpHlzh9AI9U0");
		cb.setOAuthAccessTokenSecret("X3SD7JSdbDXQCTIoSQUBv5421fxaKUiVHic8ATOOigfn4");	
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();	
		
		int nCallNum = 0;
		long nMaxId = -1;
		
		try
		{
			FileWriter fw = new FileWriter(sFileName,true);

			PrintWriter pw = new PrintWriter(fw);
			
			Query query = new Query("earthquake");
			query.setCount(100);
			query.setMaxId(449952776567537664L);
			query.setLang("en");			
			
			QueryResult result;
			result = twitter.search(query);
					
			while(result.getTweets().size() > 0 && nCallNum < 180)
			{	
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets)
				{					
					pw.print(tweet.getCreatedAt().toString());
					pw.print(",");
					pw.print("@" + tweet.getUser().getScreenName());
					pw.print(",");
					pw.print(tweet.getUser().getName());
					pw.print(",");
					pw.print("\"" + tweet.getUser().getLocation() + "\"");
					pw.print(",");
					pw.print("\"" + tweet.getText().replaceAll("(\\r|\\t|\\n|\")", "") + "\"");
					pw.print(",");
					pw.print(String.valueOf(tweet.getRetweetCount()));
					pw.print(",");
					pw.print(String.valueOf(tweet.getUser().getFollowersCount()));
					pw.print(",");
					pw.print(String.valueOf(tweet.getUser().getFriendsCount()));
					pw.print(",");
					if (tweet.getHashtagEntities().length > 0)
						pw.print("\"" + (tweet.getHashtagEntities()[0].getText()) + "\"");
					else
						pw.print("");
					pw.print(",");
					pw.print(tweet.getId());
					pw.print(",");
					pw.println("\"" + tweet.getGeoLocation() + "\"");
					if (nMaxId == -1 || tweet.getId() < nMaxId)
					{
						nMaxId = tweet.getId();
					}
				}
				if (nMaxId !=-1)
				{
					query.setMaxId(nMaxId -1);
				}
				nCallNum++;
				
				result = twitter.search(query);
			}
			
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
