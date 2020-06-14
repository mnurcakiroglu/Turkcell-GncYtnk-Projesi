package com.turkcell.Application.service;

import com.turkcell.Application.entity.Entity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class Service {
    List<Entity> CarsList;

    @Bean
    public WebMvcConfigurer configurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
    protected void DosyaOku() throws IOException {
        CarsList = new ArrayList<>();
        ClassLoader loader = Service.class.getClassLoader();
        FileReader fileReader = new FileReader(ResourceUtils.getFile(this.getClass().getResource("/degerler.txt")));
        String line;

        BufferedReader br = new BufferedReader(fileReader);

        while ((line = br.readLine()) != null) {
            String[] deger = line.split(";");
            Entity arac = new Entity();
            arac.setMarka(deger[0]);
            arac.setModel(deger[1]);
            arac.setSinif(deger[2]);
            CarsList.add(arac);
        }

        br.close();
    }

    @RequestMapping(value = "hepsi", method = RequestMethod.GET)
    public List<Entity> getCars() throws IOException {
        DosyaOku();
        return CarsList;
    }

    @GetMapping("/marka/{marka}")
    @ResponseBody
    public List<Entity> searchMarka(@PathVariable("marka") String marka, Model el) throws IOException {
        DosyaOku();
        return CarsList.stream().filter(x -> x.getMarka().toLowerCase().equals(marka.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/model/{model}")
    @ResponseBody
    public List<Entity> searchModel(@PathVariable("model") String model, Model el) throws IOException {
        DosyaOku();
        return CarsList.stream().filter(x -> x.getModel().toLowerCase().equals(model.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/sinif/{sinif}")
    @ResponseBody
    public List<Entity> searchSinif(@PathVariable("sinif") String sinif, Model el) throws IOException {
        DosyaOku();
        return CarsList.stream().filter(x -> x.getSinif().toLowerCase().equals(sinif.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/ara/{ara}")
    @ResponseBody
    public List<Entity> searchArama(@PathVariable("ara") String ara, Model el) throws IOException {
        DosyaOku();
        return CarsList.stream().filter(x -> x.getSinif().toLowerCase().contains(ara.toLowerCase()) ||
                x.getMarka().toLowerCase().contains(ara.toLowerCase()) ||
                x.getModel().toLowerCase().contains(ara.toLowerCase())).collect(Collectors.toList());
    }


}
