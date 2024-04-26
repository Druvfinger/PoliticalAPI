package com.example.politico;

import com.example.Collect.DataCollection.Collect;
import com.example.Collect.Parties.PoliticalPartiesHelper;
import com.example.politico.Services.*;
import org.hibernate.id.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MainCharacterSyndrome {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(MainCharacterSyndrome.class, args);

        SubjectService subjectService = context.getBean(SubjectService.class);
        BulletPointService bpService = context.getBean(BulletPointService.class);
        BpLogService bpLogService = context.getBean(BpLogService.class);
        PoliticalPartyService partyService = context.getBean(PoliticalPartyService.class);
        ChangeTypeService changeTypeService = context.getBean(ChangeTypeService.class);
        SubjectLogService subjectLogService = context.getBean(SubjectLogService.class);
        Collect collect = new Collect(subjectService,partyService,bpService,subjectLogService,bpLogService,changeTypeService,new PoliticalPartiesHelper());
        collect.fetchAndCommitAllSubjects();
        collect.fetchAndCommitAllPartiesBPs();

    }
}
