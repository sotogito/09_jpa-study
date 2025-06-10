package com.johnth.section03;

import com.johnth.section03.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

public class EntityEnumMappingTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeAll
    public static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa_test");
    }

    @BeforeEach
    public void initEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void destroyEntityManager() {
        entityManager.close();
    }

    @AfterAll
    public static void destroyEntityManagerFactory() {
        entityManagerFactory.close();
    }

//    @Test
//    public void enum_테스트(){
//        System.out.println(Arrays.toString(UserRole.values()));
//        System.out.println(UserRole.valueOf("ADMIN"));
//        System.out.println(UserRole.USER);
//        System.out.println(UserRole.USER.name());
//        System.out.println(UserRole.USER.ordinal());
//    }

    @Test
    public void enum_타입_매핑테스트() {
        User user1 = User.builder()
                .userId("admin")
                .userPwd("1234")
                .nickname("관리")
                .phone("010-1111-2222")
                .enrollDate(LocalDateTime.now())
                .userRole(UserRole.ADMIN)
                .build();

        User user2 = User.builder()
                .userId("user")
                .userPwd("pass")
                .nickname("관리")
                .phone("010-1111-2222")
                .enrollDate(LocalDateTime.now())
                .userRole(UserRole.USER)
                .build();

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        try {
            entityManager.persist(user1);
            entityManager.persist(user2);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
    }
}
