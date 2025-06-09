package com.sotogito.section01.simple;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;

import java.util.List;

public class SimpleJPQLTest {

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

    @DisplayName("단일 메뉴명 조회 테스트")
    @Test
    void select_menuName_by_menuCode() {
        String jpql = "SELECT m.menuName FROM menu1 m WHERE m.menuCode=7";
        String jpqlParam = "SELECT m.menuName FROM menu1 m WHERE m.menuCode=:menuCode";

//        return em.createQuery("SELECT m FROM Menu m WHERE m.menuName = :menuName", Menu.class)
//                .setParameter("menuName", menuName)
//                .getSingleResult();

        /// 1) TypedQuery
//        TypedQuery<String> query = em.createQuery(jpql, String.class);
//        TypedQuery<String> queryParam = em.createQuery(jpqlParam, String.class)
//                .setParameter("menuCode", 7);

        /// 2) Query
        Query query = em.createQuery(jpql);
        Query queryParam = em.createQuery(jpqlParam)
                .setParameter("menuCode", 7);

        String menuName = (String) query.getSingleResult();
        String menuNameParam = (String) queryParam.getSingleResult();

        System.out.println(menuName);
        System.out.println(menuNameParam);
    }

    @Test
    void select_menu_by_menuCode() {
        String jqpl = "SELECT m FROM menu1 m WHERE m.menuCode = :menuCode";

        Menu menu = em.createQuery(jqpl, Menu.class)
                .setParameter("menuCode", 7)
                .getSingleResult();

        System.out.println(menu);
    }

    @Test
    void select_menu_list_by_menuCode() {
        String jqpl = "SELECT m FROM menu1 m";

        List<Menu> menuList = em.createQuery(jqpl, Menu.class)
                .getResultList();

        System.out.println(menuList);
    }

    @Test
    void DISTINCT_select_menu() {
        String jqpl = "SELECT DISTINCT m.categoryCode FROM menu1 m";


        List<Integer> categoryList = em.createQuery(jqpl, Integer.class)
                .getResultList();

        categoryList.forEach(System.out::println);
    }

    @Test
    void IN_select_menu_by_categoryCode() {
        String jqpl = "SELECT m FROM menu1 m WHERE m.categoryCode IN (:categoryCode)";

        List<Menu> menuList = em.createQuery(jqpl, Menu.class)
                .setParameter("categoryCode", List.of(4, 6)) // IN (4,6)
                .getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    void LIKE_select_menu_by_menuName() {
        String jqpl = "SELECT m FROM menu1 m WHERE m.menuName LIKE :menuName"; /// LIKE '%마늘%'

        List<Menu> menuList = em.createQuery(jqpl, Menu.class)
                .setParameter("menuName", "%마늘%")
                .getResultList();

        menuList.forEach(System.out::println);
    }

    /**
     * ## 파라미터 바인딩
     * 1. 이름 기준 - named parameters
     * <p>
     * 2. 위치 기준 - positional parameters
     */

    @Test
    void 위치기준() {
        String jqpl = "SELECT m FROM menu1 m WHERE m.categoryCode = ?1 AND m.menuPrice > ?2";

        List<Menu> menuList = em.createQuery(jqpl, Menu.class)
                .setParameter(1, 4)
                .setParameter(2, 13000)
                .getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    void LIMIT() {
        int offset = 0;
        int display = 10;

        String jqpl = "SELECT m FROM menu1 m ORDER BY m.menuCode DESC";

        List<Menu> menuList = em.createQuery(jqpl, Menu.class)
                .setFirstResult(offset)
                .setMaxResults(display)
                .getResultList();

        menuList.forEach(System.out::println);
    }

    /**
     * ## 그룹함수 - COUNT, MAX, MIN, SUM, AVG
     * 1. 그룹 함수의 반환 타입은 정수값일 경우 Long, 실수값인 경우 Double
     * 2. 값이 없는 상태에서 COUNT 의 결과는 0, COUNT 제외 함수의 결과는 null
     *      => 반환 값을 담기 위한 변수 선언시 기본자료형으로 하게 되면 언박싱(Wrapper->Primitive)시 NPE이 발생
     */

    @Test
    void 통계함수_COUNT() {
        /**
         * COUNT
         * 1. 반환 타입 - Long
         * 2. 데이터가 없는 경우 0을 반환
         * 3. 최종 결과를 기록할 변수의 타입은 Long, long(null 불가) 을 사용해도 상관 없음
         */
        String jqpl = "SELECT COUNT(m.menuCode) FROM menu1 m WHERE m.categoryCode = :categoryCode";

        Long count = em.createQuery(jqpl, Long.class)
                .setParameter("categoryCode", 1)
                .getSingleResult();

        System.out.println(count);
    }

    @Test
    void 통계함수_SUM_AVG_MIN_MAX() {
        /**
         * 1. 반환 타입이 Long 또는 Double
         * 2. 데이터가 없을 경우 null 반환
         * 3.  최종 결과를 단는 변수를 기본자료형으로 두게 되면 NPE 발생 여지 있음
         */
        String jqpl = "SELECT SUM(m.menuPrice) FROM menu1 m WHERE m.categoryCode = :categoryCode";

        Long totalPrice = em.createQuery(jqpl, Long.class)
                .setParameter("categoryCode", 10)
                .getSingleResult();

        System.out.println(totalPrice);
    }



}
