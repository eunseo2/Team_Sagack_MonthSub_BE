package com.prgrms.monthsub.domain;

import com.prgrms.monthsub.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Pattern(regexp = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b")
    @Column(name = "email", columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String email;

    @Column(name = "username", columnDefinition = "VARCHAR(50)", nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(100)", nullable = false)
    private String password;

    @Column(name = "profile_image", columnDefinition = "VARCHAR(50)")
    private String profileImage;

    @Column(name = "profile_introduce", columnDefinition = "VARCHAR(50)")
    private String profileIntroduce;

    @Min(0)
    @Column(name = "point", columnDefinition = "BIGINT")
    private int point;

    @Column(name = "nickname", columnDefinition = "VARCHAR(50)", unique = true)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @Builder
    public User(String username, String password, Part part) {
        this.username = username;
        this.password = password;
        this.part = part;
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, password)) {
            throw new BadCredentialsException("Bad credential");
        }
    }

    public void changePart(Part part) {
        this.part = part;
    }

}
