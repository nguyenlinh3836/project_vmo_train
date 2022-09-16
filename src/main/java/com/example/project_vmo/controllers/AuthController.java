package com.example.project_vmo.controllers;

import com.example.project_vmo.models.entities.RefreshToken;
import com.example.project_vmo.models.request.LoginDto;
import com.example.project_vmo.models.request.PasswordResetLinkRequest;
import com.example.project_vmo.models.request.PasswordResetRequest;
import com.example.project_vmo.models.request.TokenRefreshRequest;
import com.example.project_vmo.models.response.JWTAuthResponse;
import com.example.project_vmo.security.jwt.JwtUtils;
import com.example.project_vmo.services.PasswordTokenService;
import com.example.project_vmo.services.RefreshTokenService;
import com.example.project_vmo.services.UserStatistService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserStatistService userStatistService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @Autowired
  private PasswordTokenService passwordTokenService;

  @PostMapping("/buyer")
  public ResponseEntity<JWTAuthResponse> authenticateBuyer(@RequestBody LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
            loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtUtils.generateJwtToken(authentication);

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(
        loginDto.getUsernameOrEmail());

    userStatistService.createStatic(loginDto.getUsernameOrEmail());

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

    jwtAuthResponse.setAccessToken(token);

    jwtAuthResponse.setRefreshToken(refreshToken.getToken());

    return ResponseEntity.ok(jwtAuthResponse);
  }

  @PostMapping("/supplier")
  public ResponseEntity<JWTAuthResponse> authenticateSupplier(@RequestBody @Valid LoginDto loginDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
            loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    userStatistService.createStatic(loginDto.getUsernameOrEmail());

    String token = jwtUtils.generateJwtToken(authentication);

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(
        loginDto.getUsernameOrEmail());

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

    jwtAuthResponse.setAccessToken(token);

    jwtAuthResponse.setRefreshToken(refreshToken.getToken());

    return ResponseEntity.ok(jwtAuthResponse);

  }

  @GetMapping("/forgot-password/confirm")
  public String confirmTokenToChangePassword(@RequestParam("token") String token) {
    return passwordTokenService.confirmTokenResetPassword(token);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> sendTokenToChangePassword(
      @RequestBody PasswordResetLinkRequest request) {
    return ResponseEntity.ok(passwordTokenService.sendTokenToChangePassword(request));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
    return ResponseEntity.ok(passwordTokenService.changePassword(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken).get();

    refreshTokenService.verifyExpiration(refreshToken);

    String token = jwtUtils.generateTokenFromUsername(refreshToken.getAccount().getUsername());

    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

    jwtAuthResponse.setAccessToken(token);

    jwtAuthResponse.setRefreshToken(refreshToken.getToken());

    return ResponseEntity.ok(jwtAuthResponse);

  }
}
