package com.example.demo.entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "itemTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
    @Id
    @SequenceGenerator(name = "item_seq_gen", sequenceName = "item_gen", initialValue = 100, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq_gen")
    @Column
    private Long itemId;
    @Column(nullable = false)
    private String itemName;
    @Column
    private String itemDescription;
    @Column(nullable = false)
    private Long itemPrice;
    @Column
    private String category;
    @Column
    private Integer discountRate;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy-hh:mm:ss")
    private Date discountStart;
    @Column
    @DateTimeFormat(pattern = "dd-MM-yyyy-hh:mm:ss")
    private Date discountEnd;
    @Column
    private Long oldPrice;
    @Column
    private Boolean salePlanned;
    @Column
    private Boolean isOnSale;
    @Column
    private Long plannedDiscountedPrice;

}
