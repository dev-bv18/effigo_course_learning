package com.example.kafkaConsumer.kafkaCon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "master_item", schema = "public")  // Change table/schema as needed
@Getter
@Setter
public class Item {

    @Id
    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "created_by", nullable = false)
    private String createdBy = "Auto";

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "estimated_price", columnDefinition = "NUMERIC DEFAULT 0")
    private BigDecimal estimatedPrice = BigDecimal.ZERO;

    @Column(name = "generic_masters", columnDefinition = "INTEGER DEFAULT 0")
    private Integer genericMasters = 0;

    @Column(name = "gl_code")
    private String glCode;

    @Column(name = "hsn_code", columnDefinition = "VARCHAR DEFAULT 'NA'")
    private String hsnCode = "NA";

    @Column(name = "item_category_id", nullable = false)
    private String itemCategoryId;

    @Column(name = "item_category_name")
    private String itemCategoryName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_sub_category_id")
    private String itemSubCategoryId;

    @Column(name = "l3_category_id")
    private String l3CategoryId;

    @Column(name = "lead_buyer_id")
    private String leadBuyerId;

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @Column(name = "masters_type", columnDefinition = "INTEGER DEFAULT 0")
    private Integer mastersType = 0;

    @Column(name = "quality", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer quality = 0;

    @Column(name = "quality_assurance", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean qualityAssurance = false;

    @Column(name = "status", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer status = 0;

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @Column(name = "type")
    private String type;

    @Column(name = "uom", nullable = false, columnDefinition = "VARCHAR DEFAULT 'NOs'")
    private String uom = "NOs";

    @Column(name = "columnitem_code")
    private String columnItemCode;

    @Column(name = "generic_item", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean genericItem = false;

    @Column(name = "cus_field_1")
    private String cusField1;

    @Column(name = "cus_field_2")
    private String cusField2;

    @Column(name = "cus_field_3")
    private String cusField3;

    @Column(name = "cus_field_4")
    private String cusField4;

    @Column(name = "cus_field_5")
    private String cusField5;

    @Column(name = "cus_field_6")
    private String cusField6;

    @Column(name = "cus_field_7")
    private String cusField7;

    @Column(name = "cus_field_8")
    private String cusField8;

    @Column(name = "cus_field_9")
    private String cusField9;

    @Column(name = "cus_field_10")
    private String cusField10;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "long_text")
    private String longText;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "partition_group")
    private String partitionGroup;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(name = "consumed_timestamp", nullable = false)
    private Timestamp consumedTimestamp = new Timestamp(System.currentTimeMillis()); // Stores when the message was consumed
}
