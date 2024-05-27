package com.example.votingService.domain.token;

import com.example.votingService.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "revoked_at", nullable = true)
    private Timestamp revokedAt;
    @Basic
    @Column(name = "expired_at", nullable = true)
    private Timestamp expiredAt;

    @Basic
    @Column(name = "token", nullable = false, unique = true)
    public String token;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = true)
    public TokenType tokenType = TokenType.BEARER;

    @Basic
    @Column(name = "is_revoked")
    public boolean isRevoked;

    @Basic
    @Column(name = "is_expired")
    public boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
