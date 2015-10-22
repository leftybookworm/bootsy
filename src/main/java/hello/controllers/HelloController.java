package hello.controllers;

import hello.models.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class HelloController {

  @RequestMapping(value="/greeting", method=RequestMethod.GET)
  public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    model.addAttribute("greeting", new Greeting());
    model.addAttribute("name",name);
    return "greeting";
  }

  /**
   * POST /greeting -> receive and process uploaded file contents and additional attributes.
   *
   * @param greeting The uploaded Multipart file.
   *
   * @return An String reference to a Thymeleaf template.
   */
  @RequestMapping(value = "/greeting", method = RequestMethod.POST)
  public String greetingSubmit(@RequestParam("uploadfile") MultipartFile uploadfile,
                               @ModelAttribute Greeting greeting,
                               Model model) {
    model.addAttribute("greeting", greeting);
    model.addAttribute("uptail", greeting.getDetail().toUpperCase());
    try(BufferedReader buffer = new BufferedReader(new InputStreamReader(uploadfile.getInputStream()))) {
      List<String> lines = buffer.lines().collect(Collectors.toList());
      lines.forEach(System.out::println);
      model.addAttribute("lines", lines);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "result";
  }
}
