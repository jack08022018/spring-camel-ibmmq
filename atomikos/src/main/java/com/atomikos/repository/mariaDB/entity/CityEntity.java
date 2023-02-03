package com.atomikos.repository.mariaDB.entity;

import com.atomikos.repository.mariaDB.entity.CountryEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "city")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "city")
    private String city;

    @Column(name = "last_update")
    private Date lastUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country_id", nullable=false)
    @JsonBackReference
    private CountryEntity country;

}
