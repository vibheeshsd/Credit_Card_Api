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
@Table(name = "state")
public class State {

    @Id
    @Column (name = " state_id")
    private int stateId;

    @Column(name = "state_name")
    private String stateName;

//    public int getId() {
//        return stateId;
//    }
//
//    public void setId(int stateId) {
//        this.stateId = stateId;
//    }
//
//    public String getState() {
//        return stateName;
//    }
//
//    public void setState(String stateName) {
//        this.stateName = stateName;
//    }

}
