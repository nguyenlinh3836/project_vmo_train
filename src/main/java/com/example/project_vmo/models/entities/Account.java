package com.example.project_vmo.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int accountId;
  @Column
  private String email;
  @Column
  private String username;
  @Column
  private String password;

  @Column
  private String fullName;

  @Column
  private String address;

  @Column
  private int age;

  @Column
  private String phone;

  @Column
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date create_at;

  @Column
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated_at;

  @Column
  private Boolean is_deleted = false;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles = new ArrayList<>();

  @OneToOne(mappedBy = "account", orphanRemoval = true)
  private UserStatist userStatist;

  @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
  private List<Good> goods;

}
