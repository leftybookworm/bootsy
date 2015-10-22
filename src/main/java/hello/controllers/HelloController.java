package hello.controllers;

import hello.models.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

@Controller
public class HelloController {

  @RequestMapping(value="/greeting", method=RequestMethod.GET)
  public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    model.addAttribute("greeting", new Greeting());
    model.addAttribute("name",name);
    return "greeting";
  }

  /**
   * POST /greeting -> receive and locally save a file and process additional model attributes.
   *
   * @param greeting The uploaded file as Multipart file parameter in the
   * HTTP request. The RequestParam name must be the same of the attribute
   * "name" in the input tag with type file.
   *
   * @return An String reference to a Thymeleaf template.
   */
  @RequestMapping(value = "/greeting", method = RequestMethod.POST)
  public String greetingSubmit(@RequestParam("uploadfile") MultipartFile uploadfile,
                               @ModelAttribute Greeting greeting,
                               Model model) {
    try {
      // Get the filename and build the local file path (be sure that the
      // application have write permissions on such directory)
      String filename = uploadfile.getOriginalFilename();
      String directory = "/Users/amcii/devels/boot/uploaded_files";
      String filepath = Paths.get(directory, filename).toString();

      // Save the file locally
      BufferedOutputStream stream =
          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
      stream.write(uploadfile.getBytes());
      stream.close();
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    model.addAttribute("greeting", greeting);
    model.addAttribute("uptail", greeting.getDetail().toUpperCase());
    return "result";
  }
}
