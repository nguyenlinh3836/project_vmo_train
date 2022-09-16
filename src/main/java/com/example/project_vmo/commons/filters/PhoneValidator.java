package com.example.project_vmo.commons.filters;

import java.util.function.Predicate;
import org.springframework.stereotype.Service;

@Service
public class PhoneValidator implements Predicate<String> {
  private final String PHONE_PATTERN = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
  @Override
  public boolean test(String phone) {
    return phone.matches(PHONE_PATTERN);
  }
}
