package com.example.project_vmo.models.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "goods")
@Where(clause = "is_deleted=0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Good {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int goodsId;
  @Column
  private String goodsName;
  @Column
  private int quantity;
  @Column
  @CreationTimestamp
  private Date create_at;

  @Column
  @UpdateTimestamp
  private Date updated_at;

  @Column(name = "is_deleted")
  private Boolean is_deleted = Boolean.FALSE;

  @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<Image> images;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "supplier_id")
  private Account account;

}
