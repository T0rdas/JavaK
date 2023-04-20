package kurswork.dealership.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String cost;

    private Long amount;

    public Long getId() {
        return id;
    }

    public String getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {return amount;}

    public void setAmount(Long amount) {this.amount = amount;}
}

