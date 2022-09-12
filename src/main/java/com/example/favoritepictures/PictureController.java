package com.example.favoritepictures;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Optional;
import java.io.IOException;

@Controller
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/")
    public String start(){
        return "redirect:/index.html";
    }

    @GetMapping("/index")
    public String index(){
        return "redirect:/index.html";
    }
    @PostMapping("/picture")
    @RequestMapping(value="/picture",method=RequestMethod.POST)
    public String uploadPicture(@RequestParam("username") String username, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        byte[] fileContent = multipartFile.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        Picture picture = new Picture();
        picture.setUserName(username);
        picture.setEncoding(encodedString);
        picture.setExtension(extension);
        picture.setFileName(multipartFile.getOriginalFilename());
        pictureRepository.save(picture);
        return "redirect:/display.html?id=" + picture.getId();
    }

    @GetMapping("/getEncoding")
    @ResponseBody
    public String getEncoding(@RequestParam("id") int id){
        Optional<Picture> p = pictureRepository.findPictureById(id);
        if(!p.isPresent()){
            return null;
        }
        Picture picture = p.get();
        String extension = picture.getExtension();
        String encoding = picture.getEncoding();
        return String.format("{ \"ext\": \"%s\", \"enc\": \"%s\" }", extension, encoding);
    }

    @RequestMapping(value="/findPicturesByUsername",method=RequestMethod.GET)
    @ResponseBody
    public String findPictures(@RequestParam("username") String username){
        Iterable<Picture> pictures = pictureRepository.findByUsername(username);
        String s = "";
        for(Picture p: pictures){
            s += String.format("<p>%d %s %s</p>",p.getId(),p.getUserName(),p.getFileName());
        }
        return s;
    }

    @GetMapping("/list")
    @ResponseBody
    public String getPictures() {
        Iterable<Picture> pictures = pictureRepository.findAll();
        String result = "<div align='center'><p>PICTURES</p><table border='1' rules='all' cellpadding='5'><thead><tr><th>ID</th><th>USER NAME</th><th>FILE</th></tr></thead><tbody>";
        for (Picture p: pictures) {
            result += String.format("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",p.getId(),p.getUserName(),p.getFileName());
        }
        result += "</tbody></table><a href='/index'>return</a></div>";
        return result;
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public String findPictureBydId(@PathVariable("id") Integer id) {
        Optional<Picture> p = pictureRepository.findPictureById(id);
        if(!p.isPresent()){
            return null;
        }
        Picture picture = p.get();
        String result = String.format("<p>%d %s %s</p>",picture.getId(),picture.getUserName(),picture.getFileName());
        result += "<a href='/index'>return</a>";
        return result;
    }

    @RequestMapping(value="/textfile",method=RequestMethod.POST)
    @ResponseBody
    public String uploadTextFile(@RequestParam("textfile") MultipartFile multipartFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        String result = "" + multipartFile.getName() + "<br>" + multipartFile.getOriginalFilename() + "<br>";
        String s = "";
        while( (s = reader.readLine()) != null ){
           result += s + "<br>";
        }
        multipartFile.transferTo(new File("/Users/dereksmith/Desktop/files/" + multipartFile.getOriginalFilename()));
        return result;
    }

}