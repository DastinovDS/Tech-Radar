package com.test.gitradar.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.gitradar.models.RepositoryLanguageModel;
import com.test.gitradar.models.RepositoryModel;
import com.test.gitradar.models.RepositoryRecordModel;
import com.test.gitradar.models.UserModel;
import com.test.gitradar.repositories.RepositoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Map;

@Service
public class RepositoryLanguageService {

    @Autowired
    RepositoryRecordRepository repositoryRecordRepository;

    @Autowired
    ApiRequestService apiRequestService;

    @Transactional
    public void addLanguagesToRecord(RepositoryRecordModel record) {

        UserModel user = record.getOwnerRepo().getOwner();
        RepositoryModel repository = record.getOwnerRepo();
        String apiString = UrlApiBuilderService.buildRepositoryLanguagesUrl(repository);

        JsonNode response = apiRequestService.performApiRequest(apiString, user.getAccessToken()).block();

        if(response != null && response.isObject()){

            Iterator<Map.Entry<String, JsonNode>> fields = response.fields();

            while (fields.hasNext()) {

                java.util.Map.Entry<String, JsonNode> field = fields.next();

                String languageName = field.getKey();
                Long bytesCount = field.getValue().asLong();

                RepositoryLanguageModel language = new RepositoryLanguageModel();
                language.setName(languageName);
                language.setSize(bytesCount);

                record.addRepositoryLanguage(language);
            }

            repositoryRecordRepository.save(record);
        }
    }
}
