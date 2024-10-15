package mergentech.internship.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "languages")
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class Language{

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "languageName")
    private String languageName;



}
