package com.chamasoft.customer;

import com.chamasoft.PassEncryption;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table( //gives us full control
 name = "customer",
 uniqueConstraints = {
         @UniqueConstraint(
                 name = "customer_phone_unique",
                 columnNames = "phone"
         )
 }
)
public class Customer{

    @Id
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq", //updated to use flyway sequence
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String phone;
    @Column(
            nullable = false
    )
    private String password;


    public Customer() {
    }

    public Customer(Integer id, String name, String phone, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;

    }

    public Customer(String name, String phone, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String email) {
        this.phone = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PassEncryption.getSecuredPassword(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

