package com.sotogito.section03.join;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class JoinJPQLTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    @BeforeAll
    static void initEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("practice-test");
    }

    @AfterAll
    static void closeEntityManagerFactory() {
        entityManagerFactory.close();
    }

    @BeforeEach
    void initEntityManager() {
        em = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    void closeEntityManager() {
        em.close();
    }

    /**
     * ## 조인 종류
     * 1. 일반 조인 : 일반적인 SQL 조인을 의미
     * 2. 페치 조인 : JPQL에서 성능 최적화를 위해 제공하는 기능
     */

    @Test
    void 내부조인() {
        String jqpl = "SELECT m FROM menu3 m"; //만약 연관관계를 LAZY로 했을 경우 조인되지 않음 -> 카테고리는 프록시에 있는상태
//        String jqpl = "SELECT m FROM menu3 m JOIN m.category";

        List<Menu> menuList = em.createQuery(jqpl, Menu.class).getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    void 외부조인() {
        String spql = "SELECT m.menuName, c.categoryName FROM menu3 m RIGHT JOIN m.category c";

        List<Object[]> menuList = em.createQuery(spql, Object[].class).getResultList();

       menuList.forEach(menu -> System.out.println(Arrays.toString(menu)));
    }

    @Test
    void 세타_조인_테스트() {
        String spql = "SELECT m.menuName, c.categoryName FROM menu3 m, category3 c";

        List<Object[]> menuList = em.createQuery(spql, Object[].class).getResultList();

        menuList.forEach(menu -> System.out.println(Arrays.toString(menu)));
    }

    @Test
    void 패치_조인() {
        /*
            select
        m1_0.menu_code,
        m1_0.category_code,
        c1_0.category_code,
        c1_0.category_name,
        c1_0.ref_category_code,
        m1_0.menu_name,
        m1_0.menu_price,
        m1_0.orderable_status
    from
        tbl_menu m1_0
    join
        tbl_category c1_0
            on c1_0.category_code=m1_0.category_code
         */
        String spql = "SELECT m FROM menu3 m JOIN FETCH m.category"; ///한번에 조인

        List<Object[]> menuList = em.createQuery(spql, Object[].class).getResultList();

        menuList.forEach(menu -> System.out.println(Arrays.toString(menu)));

        /**
         * 내부 조인으로 실행시 N+1 문제 발생
         * 1. 최초로 내부 조인 SQL문 실행시 엔티티들의 정보 조회 (1회)
         * 2. 각 Menu 엔티티별로 Cateogry 엔티티를 조회하기 위해서 Menu엔티티 개수만큼 Category엔티티 조회 SQL문 실행 (N번)
         */
    }


}
