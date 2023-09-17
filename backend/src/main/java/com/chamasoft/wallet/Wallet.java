package com.chamasoft.wallet;

import jakarta.persistence.*;

@Entity
@Table( //gives us full control
        name = "wallet"
)
public class Wallet {
    @Id
    @SequenceGenerator(
            name = "wallet_id_seq",
            sequenceName = "wallet_id_seq", //updated to use flyway sequence
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "wallet_id_seq"
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String phone;
    @Column(
            nullable = false
    )
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
