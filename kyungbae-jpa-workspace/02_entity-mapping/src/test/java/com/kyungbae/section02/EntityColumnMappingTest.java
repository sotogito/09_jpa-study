package com.kyungbae.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Date;

public class EntityColumnMappingTest {


    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory(){
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager(){
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager(){
        entityManager.close();
    }

    @AfterAll
    public static void destroyEntityManagerFactory(){
        entityManagerFactory.close();
    }

    @Test
    public void 컬럼매핑테스트(){
        User user1 = User.builder()
//                .userNo(1)
                .userId("user01")
                .userPwd("1234")
                .nickname("관리자")
                .phone("010-2020-1313")
                .address("경기도 구리시")
                .email("mail@email.com")
                .userRole("ADMIN")
                .enrollDate(new Date())
                .status("Y")
                .build();

        User user2 = User.builder()
                .userId("user02")
                .userPwd("4321")
                .nickname("사용자")
                .phone("010-3232-1212")
                .address("서울시 강동구")
                .email("mailmail@email.com")
                .userRole("USER")
                .enrollDate(new Date())
                .status("Y")
                .build();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();


        try {
            entityManager.persist(user1);
            entityManager.persist(user2);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }

    }
}
