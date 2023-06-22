package com.sahno.model.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String query;
    private String url;
    private String username;
    private String password;
    private String dbType;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)//PERSIST
    @JoinColumn(name = "dashboard_id")
    private List<DashboardRes> results;
}
