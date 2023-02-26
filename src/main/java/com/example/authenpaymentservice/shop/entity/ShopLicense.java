package com.example.authenpaymentservice.shop.entity;

import com.example.authenpaymentservice.shop.enums.ShopLicenseState;
import com.example.authenpaymentservice.shop.utils.PostgreSQLEnumType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "shop_license", schema = "shops")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class ShopLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "front_identity_card_url")
    private String frontIdentityCardUrl;

    @Column(name = "back_identity_card_url")
    private String backIdentityCardUrl;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "identity_release_date")
    private String identityReleaseDate;

    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "state")
    @Type(type = "pgsql_enum")
    @Enumerated(value = EnumType.STRING)
    private ShopLicenseState state;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "confirmed_at")
    private Timestamp confirmedAt;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
