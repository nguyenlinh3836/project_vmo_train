package com.example.project_vmo.models.response;

import com.example.project_vmo.models.entities.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
  private String accessToken;
  private String tokenType = "Bearer";

  private String refreshToken;


  public JWTAuthResponse(String accessToken) {
    this.accessToken = accessToken;
  }
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getTokenType() {
    return tokenType;
  }

}
