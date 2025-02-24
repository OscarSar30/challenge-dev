package com.chll.msa.webtoken.domain;

import com.chll.msa.webtoken.domain.enums.StatusEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("users")
@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column("user_id")
    public UUID userId;
    @Column("full_name")
    public String fullName;
    @Column("email")
    public String email;
    @Column("identification")
    public String identification;
    @Column("status")
    public StatusEnum status;
    @Column("registration_date")
    public LocalDate registrationDate;
}
