package entity;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Game implements Entity{
    @NonNull int id;
    @NonNull Engine engine;
    @NonNull Publisher publisher;
    double Price;
    String Name, Description;
}
