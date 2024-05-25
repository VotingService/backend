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
@Table(schema = "public", name = "tokens")
public class Token {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "expired_at", nullable = true)
    private Timestamp expiredAt;
    @Basic
    @Column(name = "revoked_at", nullable = true)
    private Timestamp revokedAt;
    @Basic
    @Column(name = "token", nullable = false, length = -1)
    private String token;
    @Basic
    @Column(name = "token_type", nullable = true)
    private TokenType tokenType;
    @Basic
    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked;
    @Basic
    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
