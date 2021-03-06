package hello.controllers;

import hello.models.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HelloController {

  @RequestMapping(value="/greeting", method=RequestMethod.GET)
  public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name,
                         Model model) {
    model.addAttribute("greeting", new Greeting());
    model.addAttribute("name",name);
    return "greeting";
  }

  @RequestMapping(value = "/greeting", method = RequestMethod.POST)
  public String greetingSubmit(@RequestParam("uploadfile") MultipartFile uploadfile,
                               @ModelAttribute Greeting greeting,
                               Model model) {
    model.addAttribute("greeting", greeting);
    model.addAttribute("uptail", greeting.getDetail().toUpperCase());
    try(InputStream in = uploadfile.getInputStream()) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      List<String> lines = reader.lines().collect(Collectors.toList());
      lines.forEach(System.out::println);
      model.addAttribute("lines", lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "result";
  }
}
