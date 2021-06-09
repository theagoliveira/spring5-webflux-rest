package guru.springframework.spring5webfluxrest.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Document
@Builder
public class Category {

    @Id
    private String id;
    private String name;

}
