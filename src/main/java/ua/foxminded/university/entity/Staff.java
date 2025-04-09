package ua.foxminded.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "STAFF")
public class Staff {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "First_Name")
    private String firstName;

    @Column(name = "Last_Name")
    private String lastName;

    @Column(name = "position")
    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    @Nullable
    @JoinColumn(name = "User_ID")
    private User user;
}


