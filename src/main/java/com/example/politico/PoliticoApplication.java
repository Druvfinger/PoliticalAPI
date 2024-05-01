package com.example.politico;

import com.example.Collect.DataCollection.Collect;
import com.example.Collect.Parties.PoliticalPartiesHelper;
import com.example.politico.Services.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Properties;

@SpringBootApplication
public class PoliticoApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(PoliticoApplication.class, args);

    }
    private static void fetchAllData (ConfigurableApplicationContext context) throws Exception{
        SubjectService subjectService = context.getBean(SubjectService.class);
        BulletPointService bpService = context.getBean(BulletPointService.class);
        BpLogService bpLogService = context.getBean(BpLogService.class);
        PoliticalPartyService partyService = context.getBean(PoliticalPartyService.class);
        ChangeTypeService changeTypeService = context.getBean(ChangeTypeService.class);
        var subjectLogService = context.getBean(SubjectLogService.class);
        Collect collect = new Collect(subjectService,partyService,bpService,subjectLogService,bpLogService,changeTypeService,new PoliticalPartiesHelper());
        collect.fetchAndCommitAllSubjects();
        collect.fetchAndCommitAllPartiesBPs();
    }
}
