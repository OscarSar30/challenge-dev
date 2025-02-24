package com.chll.msa.webtoken.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("tokens")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {
    @Id
    @Column("token_id")
    public UUID tokenId;
    @Column("token")
    public String token;
    @Column("generation_date")
    public LocalDateTime generationDate;
    @Column("expiration_date")
    public LocalDateTime expirationDate;
    @Column("is_used")
    public boolean isUsed;
    @Column("user_id")
    public UUID userId;
}
