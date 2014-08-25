package webapp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, 
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                int read = 0; 
                InputStream inputStream = file.getInputStream(); 
                //String content = file.getContentType();
                String fileName = file.getOriginalFilename();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File("uploadedfiles/" + fileName)));
                while ((read = inputStream.read(bytes)) != -1) { 
                	stream.write(bytes, 0, read);
                }
                stream.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !" + fileName;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }   
}  

