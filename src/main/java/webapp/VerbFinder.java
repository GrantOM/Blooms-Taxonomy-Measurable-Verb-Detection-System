package webapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.*;

//This will get our verbs from a text file in root until I figure out
// how to use h2 for an embedded database
public class VerbFinder {
	
	private ResultsHandler rH;
	
	private BufferedReader buf;
	private ArrayList<String[]> taxonomy;
	private String lineJustFetched;
	private String[] verbArray;
	
    //Constructor
	public VerbFinder() throws FileNotFoundException{
		
		rH = new ResultsHandler();
		
		buf = new BufferedReader(new FileReader("Verbs.txt"));
        taxonomy = new ArrayList<>();
        lineJustFetched = null;
        
        try{
            
            while(true){
            	
                lineJustFetched = buf.readLine();
                
                if(lineJustFetched == null){  
                	
                    break; 
                }else{
                	
                    verbArray = lineJustFetched.split("\t");
                    taxonomy.add(verbArray);
                }
            }

            buf.close();
            
        }catch(IOException e) {
    		e.printStackTrace();
        }
        
	}
	
	
	//Takes the text file string as a parameter
    public void findVerbs(String fileText ) throws JSONException, IOException {
    	
    	JSONObject results = new JSONObject();
    	JSONArray resultY = new JSONArray();
    	JSONObject resultX = new JSONObject();
    	int count = 0;
    	
    	File file = new File("Results.txt");

    	fileText = fileText.toLowerCase();
    	
    	for (int i = 0; i < taxonomy.get(0).length; i++){
    		
    		for (int j = 1; j < taxonomy.size(); j++){
    			
    			if (taxonomy.get(j)[i] != ""){
    				
    				count = StringUtils.countMatches(fileText, taxonomy.get(j)[i]); 
    				
    				if (count > 0){	
    					
    					resultX.put("result", count);
    					resultX.put("verb", taxonomy.get(j)[i]);
    				}
    			}	
    			
    			if (count > 0){
    				
    				resultY.put(resultX);
    				resultX = new JSONObject();
    			}
    		}
    		
    		results.put(taxonomy.get(0)[i] , resultY);
    		resultY = new JSONArray();
    	}
    	
    	if (!file.exists()) {
			file.createNewFile();
		}
    		
    	FileWriter fw = new FileWriter(file.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(results.toString() + "\t");
    	bw.close();
    		
    	rH.Refresh();
    
    } 
    
    public JSONObject[] GetResults() throws JSONException{
    	
    	return rH.GetResults();
    }
}   