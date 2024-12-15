package com.example.CreditCard.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Entity
@Table(name = "city")

public class City {

    @Id
    @Column(name = " city_id")
    private int cityId;

    @Column(name = "city_name")
    private String cityName;

    @Override
    public String toString() {
        return "City id=" + cityId;
    }

}
