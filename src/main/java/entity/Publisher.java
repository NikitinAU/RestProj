package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Publisher implements Entity{
    int id;
    String Name;
    Date Creation_Date;

}
