package com.johnth.section03;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/*
    ## 컬럼 매핑시 @Column에 사용 가능한 속성들 ##
    1. name             : 매핑할 테이블의 컬럼명
    2. insertable       : 엔티티 저장시 필드 저장 여부 (default:true)
    3. updatable        : 엔티티 수정시 필드 수정 여부 (default:true)
    4. table            : 엔티티와 매핑될 테이블 이름, 하나의 엔티티를 두개 이상의 테이블에 매핑할 때 사용(@SecondaryTable사용)
    5. nullable         : null 허용 여부 설정, not null 제약조건에 해당함 (default:true)
    6. unique           : 컬럼의 유일성 제약조건에 해당함 (default:false), 두 개 이상의 컬럼을 묶어 부여하고자 할 경우 클래스 레벨에서 uniqueConstraints 속성을 활용
    7. columnDefinition : 직접 컬럼의 DDL을 지정
    8. length           : 문자열 길이, String 타입에서만 사용 (default:255)

 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name="tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_no")
    private int userNo;
    @Column(name="user_id", nullable = false)
    private String userId;
    @Column(name="user_pwd", nullable = false)
    private String userPwd;
    @Column(name="nickname", nullable = false)
    private String nickname;
    @Column(name="phone", columnDefinition = "varchar(13) default '010-0000-0000'")
    private String phone;
    @Column(name="email", unique = true)
    private String email;
    @Column(name="address")
    private String address;

    @Column(name="enroll_date")
    private LocalDateTime enrollDate;   // datetime

    @Column(name="user_role")
    @Enumerated(EnumType.STRING) //Ordinal : 순서 , String: 이름
    private UserRole userRole;
    @Column(name="status", length=1)
    private String status;
}

/*
    ## @Enumerated ##
    1. Enum 타입 매핑을 위해 사용
    2. 종류
       1) EnumType.ORDINAL : 상수 정의 순서로 매핑함 (0부터 시작), 생략시 기본값
       2) EnumType.STRING  : 상수명을 문자열로 매핑함
    3. 종류별 장단점
       1) EnumType.ORDINAL : 데이터베이스에 저장되는 데이터의 크기가 작음. 단, 이미 저장된 enum의 순서를 변경할 수 없음
       2) EnumType.STRING  : 데이터베이스에 저장되는 데이터의 크기가 큼.   단, 저장된 enum의 순서가 바뀌거나 새로운 enum이 추가되어도 안전함
 */
