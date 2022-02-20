package com.assignment.warehouse.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @GeneratedValue
    private int prod_id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="fk_prod_id", referencedColumnName = "prod_id")
    private List<contain_articles> contain_articles;
}
