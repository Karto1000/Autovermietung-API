package org.bbzsogr.autovermietungapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bbzsogr.autovermietungapi.model.Car;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO implements IntoEntity<Car> {
    @NotNull(message = "Brand is required")
    @NotBlank(message = "Brand must not be blank")
    private String brand;

    @NotNull(message = "Model is required")
    @NotBlank(message = "Model must not be blank")
    private String model;

    @NotNull(message = "Price per hour is required")
    private Double pricePerHour;

    private byte[] picture;

    @Override
    public Car intoEntity() {
        return Car.builder()
                .brand(brand)
                .model(model)
                .pricePerHour(pricePerHour)
                .picture(picture)
                .build();
    }
}