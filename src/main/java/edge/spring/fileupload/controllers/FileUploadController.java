package edge.spring.fileupload.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(
			@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file){
        
		// No name passed
		if(name.isEmpty()) {
			return "Cannot upload a file without giving a name!";
		}
		
		if (!file.isEmpty()) {
            try {
            	// Write the uploaded file to disk
                byte[] bytes = file.getBytes();
                
                // We can handle file location here if necessary
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("./uploads/" + name)));
                
                stream.write(bytes);
                stream.close();
                
                // Success message returned to user
                return "You successfully uploaded " + name;
            } catch (IOException e) {
            	// IO Exception fails the upload
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
        	// User tries to upload an empty file
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
