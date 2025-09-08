package com.example.demo.repositories;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import com.example.demo.models.QuestionElasticDocument;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface QuestionDocumentRepository extends ReactiveElasticsearchRepository<QuestionElasticDocument, String> {

    Flux<QuestionElasticDocument> findByTitleContainingOrContentContaining(String title, String content);
}