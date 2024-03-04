package com.api.entities.residence;

import com.api.entities.BaseEntity;
import com.api.entities.users.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table(name = "countries")
@EqualsAndHashCode(callSuper = true)
public class Country extends BaseEntity {
  @Column(value = "name")
  private String name;

  @Column(value = "phone_preffix")
  private String phonePreffix;

  @Transient
  private List<User> users;
}
