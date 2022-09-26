package com.example.project_vmo.models.entities;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.UpdateTimestamp;

@Table
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserStatist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int staticId;
  @Column
  private int count;
  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column
  @UpdateTimestamp
  private Date lastLogin;
}
