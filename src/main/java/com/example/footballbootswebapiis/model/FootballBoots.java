package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.mappers.BrandConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Validated
@Entity
@Table(name = "footballboots")
@EqualsAndHashCode
public class FootballBoots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String name;
    @Column(columnDefinition = "varchar(255)")
    private String description;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = BrandConverter.class)
    private Brand brand;
    @Column(nullable = false)
    private String photoPath;
    @Column(nullable = false)
    private String bigPhotoPath;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "joinbetweenbootsandbootsattributes",
            joinColumns = @JoinColumn(name = "boots_id"),
            inverseJoinColumns = @JoinColumn(name = "boots_attributes_id")
    )
    private List<FootballBootsAttributes> footballBootsAttributesList = new ArrayList<>();

    public FootballBoots(String name, String description, Brand brand, String photo, String bigPhotoPath, List<FootballBootsAttributes> footballBootsAttributesList) {
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.photoPath = photo;
        this.bigPhotoPath = bigPhotoPath;
        this.footballBootsAttributesList.addAll(footballBootsAttributesList);
    }

    public int getQuantityBySize(int size) {
        for (FootballBootsAttributes footballBootsAttributes : this.footballBootsAttributesList) {
            if (footballBootsAttributes.getSize() == size) {
                return footballBootsAttributes.getQuantity();
            }
        }

        return 0;
    }
}
