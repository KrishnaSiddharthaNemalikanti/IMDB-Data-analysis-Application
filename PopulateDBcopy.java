import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;


public class PopulateDBcopy {
    public static void main(String args[]) throws IOException {

         Connection con = null;

        try {
           con = openConnection();

           // populateMovies(con);
           // populateMovie_Countries(con);
            populateTags(con);
          //   populateUser_TaggedMovies_timestamp(con);
          //   populateMovie_Genres(con);
          //populate_mactors(con);
         //   populateUser_RatedMovies_timestamps(con);
        // populateMovie_Directors(con);
       //   populateMovieTags(con);
        //  populateLocations(con);

        } catch (SQLException e) {
            System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot find the database driver");
        } finally {
            // Never forget to close database connection
            closeConnection(con);
        }
    }
    public static void populateMovies(Connection dbConnection){
        BufferedReader br6 = null;
        FileReader fr6 = null;
        String FILENAME6 = "/Users/snemalik/Desktop/movies.dat";
        try {
            fr6 = new FileReader(FILENAME6);
            br6 = new BufferedReader(fr6);
            String sCurrentLine;
            String id6;
            String title;
            String imdbID;
            String spanishTitle;
            String imdbPictureURL;
            String year;
            String rtID;
            String rtAllCriticsRating;
            String rtAllCriticsNumReviews;
            String rtAllCriticsNumFresh;
            String rtAllCriticsNumRotten;
            String rtAllCriticsScore;
            String rtTopCriticsRating;
            String rtTopCriticsNumReviews;
            String rtTopCriticsNumFresh;
            String rtTopCriticsNumRotten;
            String rtTopCriticsScore;
            String rtAudienceRating;
            String rtAudienceNumRatings;
            String rtAudienceScore;
            String rtPictureURL;
            String sql_statement;
            PreparedStatement ps = null;
            br6.readLine();
            while ((sCurrentLine = br6.readLine()) != null) {
                String delimiter[] = sCurrentLine.split("\t");
                if (delimiter.length == 21) {
                    id6 = delimiter[0];
                    title = delimiter[1];
                    imdbID = delimiter[2];
                    spanishTitle = delimiter[3];
                    imdbPictureURL = delimiter[4];
                    year = delimiter[5];
                    rtID = delimiter[6];
                    rtAllCriticsRating = delimiter[7];
                    rtAllCriticsNumReviews = delimiter[8];
                    rtAllCriticsNumFresh = delimiter[9];
                    rtAllCriticsNumRotten = delimiter[10];
                    rtAllCriticsScore = delimiter[11];
                    rtTopCriticsRating = delimiter[12];
                    rtTopCriticsNumReviews = delimiter[13];
                    rtTopCriticsNumFresh = delimiter[14];
                    rtTopCriticsNumRotten = delimiter[15];
                    rtTopCriticsScore = delimiter[16];
                    rtAudienceRating = delimiter[17];
                    rtAudienceNumRatings = delimiter[18];
                    rtAudienceScore = delimiter[19];
                    rtPictureURL = delimiter[20];
                    sql_statement = "INSERT INTO movies VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try {
                        ps = dbConnection.prepareStatement(sql_statement);
                        ps.setString(1, id6);
                        ps.setString(2, title);
                        ps.setString(3, imdbID);
                        ps.setString(4, spanishTitle);
                        ps.setString(5, imdbPictureURL);
                        ps.setString(6, year);
                        ps.setString(7, rtID);
                        ps.setString(8, rtAllCriticsRating);
                        ps.setString(9, rtAllCriticsNumReviews);
                        ps.setString(10, rtAllCriticsNumFresh);
                        ps.setString(11, rtAllCriticsNumRotten);
                        ps.setString(12, rtAllCriticsScore);
                        ps.setString(13, rtTopCriticsRating);
                        ps.setString(14, rtTopCriticsNumReviews);
                        ps.setString(15, rtTopCriticsNumFresh);
                        ps.setString(16, rtTopCriticsNumRotten);
                        ps.setString(17, rtTopCriticsScore);
                        ps.setString(18, rtAudienceRating);
                        ps.setString(19, rtAudienceNumRatings);
                        ps.setString(20, rtAudienceScore);
                        ps.setString(21, rtPictureURL);
                        ps.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("FAILED PREPAPREDSTATEMENT FOR TABLE MOVIES");
                        e.printStackTrace();
                        return;
                    }
                } else {
                    System.out.println("COLUMN MISMATCH FOR MOVIES TABLE");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (br6 != null)
                    br6.close();
                if (fr6 != null)
                    fr6.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

}
 private static void populateMovie_Genres(Connection connect) throws SQLException {
        Statement stmt1 = connect.createStatement();
        stmt1.executeUpdate("DELETE FROM movie_genres");
        FileRead fR = new FileRead("/Users/snemalik/Desktop/movie_genres.dat");
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;

            PreparedStatement stmt = connect.prepareStatement("INSERT INTO movie_genres VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }
    private static void populate_mactors(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.println("Deleting previous tuples ...");
		stmt.executeUpdate("DELETE FROM m_actors");
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO m_actors VALUES(?,?,?,?)");
		try{
			File file=new File("/Users/snemalik/Desktop/DatUC/movie_actors.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l= br.readLine();
			String[] str={};

			while((l=br.readLine())!=null)
			{
				str=l.split("\\s");
				for(int j=3;j<str.length-1;j++)
				{
					str[2]+=" "+str[j]+" ";
				}
				str[3]=str[str.length-1];

				   stmt1.setString(1,str[0]);
		           stmt1.setString(2,str[1]);
		           stmt1.setString(3,str[2]);
		           stmt1.setString(4,str[3]);

		           stmt1.executeUpdate();

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close();
		stmt1.close();
	}
    private static void populateMovie_Countries(Connection connect) throws SQLException {
        Statement stmt1 = connect.createStatement();
        stmt1.executeUpdate("DELETE FROM movie_countries");
        FileRead fR = new FileRead("/Users/snemalik/Desktop/movie_countries.dat");
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;

            PreparedStatement stmt = connect.prepareStatement("INSERT INTO movie_countries VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }

	private static void populateUser_TaggedMovies_timestamp(Connection con) throws SQLException {
	Statement stmt = con.createStatement();
	System.out.println("Deleting previous tuples ...");
	stmt.executeUpdate("DELETE FROM userIDmovieIDtagIDtimestamp");
	PreparedStatement stmt1 = con.prepareStatement("INSERT INTO userIDmovieIDtagIDtimestamp VALUES(?,?,?,?)");
	try{
		File file=new File("/Users/snemalik/Desktop/user_taggedmovies-timestamps.dat");
		FileReader f=new FileReader(file);
		BufferedReader br=new BufferedReader(f);

		String l = br.readLine();
		String[] str={};
		while((l=br.readLine())!=null)
		{    str=l.split("\\s");
			 stmt1.setString(1,str[0]);
	         stmt1.setString(2,str[1]);
	         stmt1.setString(3,str[2]);
	         stmt1.setString(4,str[3]);

	         stmt1.executeUpdate();

		}
	}
	catch(IOException e){
		e.printStackTrace();
	}
	stmt.close();
	stmt1.close();
}

    private static void populateTags(Connection connect) throws SQLException {
        Statement stmt1 = connect.createStatement();
        stmt1.executeUpdate("DELETE FROM tags");
        FileRead fR = new FileRead("/Users/snemalik/Desktop/tags.dat");
        String[] str = null;
        try {
            str = fR.openFile();

            String[] words = null;

            PreparedStatement stmt = connect.prepareStatement("INSERT INTO tags VALUES(?,?)");
            for(int i = 1; i < str.length; i++)
            {
                words = str[i].split("\\s");
                for(int j = 2; j < words.length; j++)
                {
                    words[1] += " " + words[j] + " ";
                }
                if(words.length<2)
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, " ");
                }
                else
                {
                    stmt.setString(1, words[0]);
                    stmt.setString(2, words[1]);
                }
                stmt.executeUpdate();
            }
            stmt.close();
            stmt1.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }
    private static void populateUser_RatedMovies_timestamps(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.println("Deleting previous tuples ...");
		stmt.executeUpdate("DELETE FROM userratedmoviestimestamps");
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO userratedmoviestimestamps VALUES(?,?,?,?)");
		try{
			File file=new File("/Users/snemalik/Desktop/DatUC/user_ratedmovies-timestamps.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l= br.readLine();
			String[] str={};

			while((l=br.readLine())!=null)
			{


				str=l.split("\\s");

				 stmt1.setString(1,str[0]);
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]);
		         stmt1.setString(4,str[3]);

		         stmt1.executeUpdate();

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close();
		stmt1.close();
	}
    private static void populateMovieTags(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.println("Deleting previous tuples ...");
		stmt.executeUpdate("DELETE FROM movie_tags");
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO movie_tags VALUES(?,?,?)");
		try{
			File file=new File("/Users/snemalik/Desktop/DatUC/movie_tags.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l = br.readLine();
			String[] str={};
			while((l=br.readLine())!=null)
			{



				str=l.split("\\s");

				 stmt1.setString(1,str[0]);
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]);

		         stmt1.executeUpdate();

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close();
		stmt1.close();
	}
    private static void populateMovie_Directors(Connection con) throws SQLException {
		Statement stmt = con.createStatement();
		System.out.println("Deleting previous tuples from m_directors tables ...");
		stmt.executeUpdate("DELETE FROM m_directors");
		PreparedStatement stmt1 = con.prepareStatement("INSERT INTO m_directors VALUES(?,?,?)");
		try{
			File file=new File("/Users/snemalik/Desktop/DatUC/movie_directors.dat");
			FileReader f=new FileReader(file);
			BufferedReader br=new BufferedReader(f);

			String l = br.readLine();
			String[] str={};
			int count=0;

			while((l=br.readLine())!=null)
			{

				count++;

				str=l.split("\\s");
				for(int j=3;j<str.length;j++)
				{
					str[2]+=" "+str[j]+" ";
				}


				 stmt1.setString(1,str[0]);
		         stmt1.setString(2,str[1]);
		         stmt1.setString(3,str[2]);



		           stmt1.executeUpdate();

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		stmt.close();
		stmt1.close();
	}
    private static void populateLocations(Connection con) throws SQLException {

			Statement stmt = con.createStatement();
			System.out.println("Deleting previous tuples ...");
			stmt.executeUpdate("DELETE FROM movie_locations");
			stmt.close();
			PreparedStatement ps = con.prepareStatement("INSERT INTO movie_locations VALUES(?,?,?,?,?)");
			File file=new File("/Users/snemalik/Desktop/DatUC/movie_locations.dat");
			try{
			FileReader f=new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			String sCurrentLine;
            String id3;
            String location1;
            String location2;
            String location3;
            String location4;
            String sql_statement;
            br.readLine();
            while ((sCurrentLine = br.readLine()) != null) {
                String delimiter[] = sCurrentLine.split("\t");
                id3 = (delimiter.length > 0) ? delimiter[0] : "";
                location1 = (delimiter.length > 1) ? delimiter[1] : "";
                location2 = (delimiter.length > 2) ? delimiter[2] : "";
                location3 = (delimiter.length > 3) ? delimiter[3] : "";
                location4 = (delimiter.length > 4) ? delimiter[4] : "";
                sql_statement = "INSERT INTO movie_locations " + "VALUES (?, ?, ?, ?, ?)";
                try {
                	ps = con.prepareStatement(sql_statement);
                    ps.setString(1, id3);
                    ps.setString(2, location1);
                    ps.setString(3, location2);
                    ps.setString(4, location3);
                    ps.setString(5, location4);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("FAILED PREPAPREDSTATEMENT FOR TABLE LOCATIONS");
                    e.printStackTrace();
                    return;
                } finally {
                    if (stmt != null) {
                        try {
                        	stmt.close();
                        } catch (SQLException ex) {
                        }
                    }
                }}}catch(IOException e){
        			e.printStackTrace();
        		}}


    private static Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String host = "localhost";
        String port = "3306";
        String dbName = "siddhu";
        String userName = "root";
        String password = "NEWPASSWORD";

        // Construct the JDBC URL
        String dbURL = "jdbc:mysql:" +"//"+ host + ":" + port + "/" + dbName;
        System.out.println(dbURL);
        return DriverManager.getConnection(dbURL, userName, password);
    }

    /**
     * Close the database connection
     */
    private static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println("Cannot close connection: " + e.getMessage());
        }
    }
}
