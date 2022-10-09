package com.example.project_vmo.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
public class Categories {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int categoryId;

  @Column
  private String categoryName;

  @Column
  private boolean is_delete;

  @OneToMany(mappedBy = "categories", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Good> goods = new ArrayList<>();

  @Column
  @CreationTimestamp
  private Date createAt;

  @Column
  @UpdateTimestamp
  private Date updatedAt;
}
