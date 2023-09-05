package com.project.onlinecompilerbackend.Controller;

import com.project.onlinecompilerbackend.Model.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "https://shiv-1203.github.io/online-compiler")
@RequestMapping("/api")
public class CodeInputController {
    @PostMapping(path = "/postCode")
    public String postCode(@RequestBody Data data){
        String code = data.getCode();
        String language = data.getLanguage();
        String input = data.getInput();
        System.out.println(code);
        System.out.println(input);
        System.out.println(language);

        if("python".equals(language)){
            language="py";
        }

        Data newData = new Data(code,language,input);
        String url = "https://codexweb.netlify.app/.netlify/functions/enforceCode";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Data> entity = new HttpEntity<>(newData, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred during compilation.";
        }
    }
}
