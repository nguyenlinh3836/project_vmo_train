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
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "OrderItem")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int orderId;

  @Column(name = "status")
  private String status;

  @Column(name = "message")
  private String message;

  @Column
  @CreationTimestamp
  private Date createAt;

  @Column
  @UpdateTimestamp
  private Date updatedAt;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "orderDetail",
      joinColumns = @JoinColumn(name = "accountId"),
      inverseJoinColumns = @JoinColumn(name = "orderId")
  )
  private List<Account> accounts = new ArrayList<>();
}
