package webapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.*;

//This will get our verbs from a text file in root until I figure out
// how to use h2 for an embedded database
public class VerbFinder {
	
	BufferedReader buf;
    ArrayList<String[]> taxonomy;
    String lineJustFetched;
    String[] verbArray;
	
	public VerbFinder() throws FileNotFoundException{
		
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
        }catch(Exception e){
            //Pretend it didn't happen
        }
        
	}
	
	//Old method for testing
	public void spitOut(){
		
		for (int i = 0; i < taxonomy.size(); i++){
			
				System.out.print(taxonomy.get(i).length);
			
			System.out.print("\n");
		}	
	}
	
	//Takes the text file string as a parameter
    public JSONObject findVerbs(String fileText ) throws JSONException {
    	
    	JSONObject results = new JSONObject();
    	JSONArray resultY = new JSONArray();
    	JSONObject resultX = new JSONObject();
    	int count = 0;

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
    	
        return results;
    } 
}   