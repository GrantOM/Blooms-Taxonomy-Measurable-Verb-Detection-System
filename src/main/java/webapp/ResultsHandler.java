package webapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.*;

public class ResultsHandler {
	
	private JSONObject[] ResultsArray;
	
	private BufferedReader buf;
	private String lineJustFetched;
	private String entireContents = "";
	private String[] StringResultsArray;
	
	public void Refresh() throws FileNotFoundException{
		
		buf = new BufferedReader(new FileReader("Results.txt"));
	    lineJustFetched = null;
	    
	    try{
	        
	        while(true){
	        	
	            lineJustFetched = buf.readLine();
	            
	            if(lineJustFetched == null){  
	            	
	                break; 
	                
	            }else{
	            	
	                entireContents += lineJustFetched;
	    
	            }
	        }
	        
	        StringResultsArray = entireContents.split("\t");
	
	        buf.close();
	        
	    }catch(IOException e) {
			e.printStackTrace();
	    }
	}
	
	public JSONObject[] GetResults() throws JSONException {
		
		ResultsArray = new JSONObject[StringResultsArray.length];
		
		for (int i = 0; i < StringResultsArray.length; i++) {
			
			
			ResultsArray[i] = new JSONObject(StringResultsArray[i]);
			
		}
		
		return ResultsArray;
		
	}

}
