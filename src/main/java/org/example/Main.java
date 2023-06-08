package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Give me the country in small latters and without mistakes");
            String country = scanner.nextLine();
       GetRequest getRequest = Unirest.get("https://restcountries.com/v2/name/"+country);
            HttpResponse<String> response = getRequest.asString();
            System.out.println(response.getStatus());
            System.out.println("--------");
            String json = response.getBody();
            //200-OK מעולה
            //404 - NOT FOUND הבעיה אצלנו
            //500 - SERVER ISSUE אנחנו עשינו הכל טוב, הבעיה היא אצל הספק

            ObjectMapper om = new ObjectMapper();  //ליצור אובייקט מג'קסון שימפה את הג'ייסון
            try {
                List<CountryModel> cm= om.readValue(json, new TypeReference<>() {});  //קורא את הערך מהג'ייסון (סטרינג) ויוצר לי קלאס או רשימה שהגדרתי מראש
                System.out.println(cm.get(0).getBorders());

                List<Integer> population = new ArrayList<>();
                System.out.println(population);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}