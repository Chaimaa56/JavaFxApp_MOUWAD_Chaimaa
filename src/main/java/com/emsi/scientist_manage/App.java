package com.emsi.scientist_manage;

import com.emsi.scientist_manage.entities.Scientist;
import com.emsi.scientist_manage.service.ScientistService;

public class App {
    public static void main( String[] args )
    {
        ScientistService scientistService = new ScientistService();
        for( Scientist scientist :scientistService.findAll())
            System.out.println(scientist);
    }


}

