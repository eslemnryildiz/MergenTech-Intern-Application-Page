package mergentech.internship.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "statuses")
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class Status {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "statusName")
    private String statusName;
}
