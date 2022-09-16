package com.example.project_vmo.models.entities;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resetPasswordToken")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column
  private String token;

  @ManyToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private Account account;

  @Column
  private LocalDateTime expiryDate;

  @Column
  private LocalDateTime confirmedAt;

  @Column
  private Boolean active;


}
