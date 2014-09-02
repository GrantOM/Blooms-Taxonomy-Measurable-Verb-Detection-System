package webapp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WebAppController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    /*@RequestMapping(method=RequestMethod.GET)
    public @ResponseBody String retrieveText() {
        return "You can upload a file by posting to this same URL.";
    }*/
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody SubmissionModel handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        SubmissionModel submission = new SubmissionModel();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                int read = 0; 
                //String line = null;
                
                List<String> fileText = new ArrayList<String>();
                String fileName = file.getOriginalFilename();
                InputStream inputStream = file.getInputStream(); 
 
            	Metadata metadata = new Metadata();
                BodyContentHandler ch = new BodyContentHandler();
            	AutoDetectParser parser = new AutoDetectParser();
            	
            	String mimeType = new Tika().detect(fileName);
                metadata.set(Metadata.CONTENT_TYPE, mimeType);
                
                parser.parse(inputStream, ch, metadata, new ParseContext());
                
                for (int i = 0; i < metadata.names().length; i++) {
                    String item = metadata.names()[i];
                    fileText.add(item + " -- " + metadata.get(item));
                }
    
                BufferedOutputStream stream = 
                		new BufferedOutputStream(new FileOutputStream(new File("uploadedfiles/" + fileName)));
                while ((read = inputStream.read(bytes)) != -1) { 
                	stream.write(bytes, 0, read);               	
                }
                
                stream.close();
                inputStream.close();

                /*BufferedReader br = new BufferedReader(new FileReader("uploadedfiles/" + fileName));
                while ((line = br.readLine()) != null) {
                	fileText.add(line);
                }
                br.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !" + fileName +
                		fileText + "\n" + ch.toString();*/
                submission.setContent(ch.toString());
                return submission;
            } catch (Exception e) {
                //return "You failed to upload " + name + " => " + e.getMessage();
                return submission;
            }
        } else {
            return submission;
        }
    }

}

