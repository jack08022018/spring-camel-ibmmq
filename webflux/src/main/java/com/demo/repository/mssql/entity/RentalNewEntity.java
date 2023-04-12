package com.demo.repository.mssql.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "rental_new")
public class RentalNewEntity implements Serializable {
//    @JsonIgnore
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rental_seq")
//    @SequenceGenerator(name="rental_seq", sequenceName="rental_seq", allocationSize = 50)
    @Column("rental_id")
    private Integer rentalId;

//    @JsonIgnore
//    @Column("rental_date")
//    private Date rentalDate;
//
//    @Column("inventory_id")
//    private Integer inventoryId;
//
//    @JsonIgnore
//    @Column("customer_id")
//    private Integer customerId;
//
//    @JsonIgnore
//    @Column("return_date")
//    private Date returnDate;
//
//    @JsonIgnore
//    @Column("staff_id")
//    private Integer staffId;
//
//    @JsonIgnore
//    @Column("last_update")
//    private Date lastUpdate;

//    @JsonIgnore
    @Column("status")
//    @Convert(converter = StatusConverter.class)
    private String status;

}
