package br.senac.rj.edumysql.praJa.entity;

import br.senac.rj.edumysql.praJa.Enum.RoleName;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name ="role_name")
    private RoleName name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}

