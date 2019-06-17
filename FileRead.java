import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {
	
	File path;
	
	public FileRead(String str)
	{
		path = new File(str);
	}
	
	public String[] openFile() throws IOException
	{
		FileReader filereader = new FileReader(path);
		BufferedReader textreader = new BufferedReader(filereader);
		int numberOfLines = readLines();
		String[] textData = new String[numberOfLines];
		for(int i=0;i<numberOfLines;i++){
			textData[i] = textreader.readLine();
		}
		textreader.close();
		return textData;
	}
	
	public int readLines() throws IOException
	{
		FileReader filereader = new FileReader(path);
		BufferedReader bf = new BufferedReader(filereader);
		String line;
		int numberOflines = 0;
		while((line = bf.readLine()) != null){
			numberOflines++;
		}
		bf.close();
		return numberOflines;
	}
	
	public static void main(String[] args) throws IOException
	{
	}
}