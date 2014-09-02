package webapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubmissionModel {

    private String content;

    public SubmissionModel(){

    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    //TODO: Metadata retrieval and modifying
}
