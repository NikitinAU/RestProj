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
public class User implements Entity{
    private int id;
    private String username, email;
    private Date Join_Date;
}
