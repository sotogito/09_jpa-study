package com.podoseee.section03.join;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class JoinJPQLTest {

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

    /*
        ## 조인 종류 ##
        1. 일반 조인 : 일반적인 SQL 조인을 의미
        2. 페치 조인 : JPQL에서 성능 최적화를 위해 제공하는 기능
     */

    @Test
    public void 내부_조인_테스트() {
        // 연관관계 매핑 되어있지 않으면 아래 JPQL은 사용할 수 없습니다.
        // String jpql = "SELECT FROM menu3 m JOIN category3 c ON m.categoryCode = c.categoryCode";

        // 연관관계 매핑이 되어 있는 경우에는 이렇게 쿼리를 작성할 수 있습니다.
        String jpql = "SELECT m FROM Menu3 m JOIN m.category c";

        List<Menu3> menuList = entityManager.createQuery(jpql, Menu3.class).getResultList();

        menuList.forEach(System.out::println);
    }

    @AfterEach
    public void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @AfterAll
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void 외부_조인_테스트(){
        // 메뉴가 없는 카테고리도 조회
        String jpql = "SELECT m.menuName, c.categoryName FROM menu3 m RIGHT JOIN m.category c";

        List<Object[]> menuList = entityManager.createQuery(jpql, Object[].class).getResultList();

        menuList.forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }
}