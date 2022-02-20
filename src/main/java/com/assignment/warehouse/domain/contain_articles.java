package com.assignment.warehouse.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "contain_articles")
public class contain_articles {

    @Id
    @GeneratedValue
    private int ArticleId;

    private int art_id;

    private int amount_of;

}
