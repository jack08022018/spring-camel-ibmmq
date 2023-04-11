package com.demo.repository.mssql.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
    @SequenceGenerator(name = "rental_seq", sequenceName = "rental_seq", allocationSize = 50)
    @Column("rental_id")
    private Integer rentalId;

    @Column("rental_date")
    private Date rentalDate;

    @Column("inventory_id")
    private Integer inventoryId;

    @Column("customer_id")
    private Integer customerId;

    @Column("return_date")
    private Date returnDate;

    @Column("staff_id")
    private Integer staffId;

    @Column("last_update")
    private Date lastUpdate;

//    @Column(name = "status")
//    @Convert(converter = StatusConverter.class)
//    private Status status;

}
