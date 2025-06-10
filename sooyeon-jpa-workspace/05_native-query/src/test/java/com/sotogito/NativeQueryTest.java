package com.sotogito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

public class NativeQueryTest {

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
     * ## Native Query
     * 1. SQL 쿼리를 그대로 사용하는 걸 의미
     * 2. ORM 기능을 이용하면서 SQL 쿼리도 활용할 수 있어 강력하게 DB 접근이 가능
     * 3. 복잡한 쿼리를 작성해야되거나, 특정 데이터베이스에서만 사용 가능한 기능을 서야될 때 Native Query 활용
     * 4. Natuve Qery API
     * 1) 결과 타입 명시
     * Query createNativeQuery(String sqlString, Class resultClass)
     * 2) 결과 타입 명시 X
     * Query createNativeQuery(String sqlString)
     * 3) 결과 매핑 사용
     * Query createNativeQuery(String sqlString, String resultSetMapping)
     */

    @Test
    void 결과_타입_명시_반환() {
        int menuCode = 15;

        String sql = "SELECT menu_code, menu_name, menu_price, category_code, orderable_status FROM tbl_menu WHERE menu_code = ?1";

        Menu foundedMenu = (Menu) em.createNativeQuery(sql, Menu.class)
                .setParameter(1, menuCode)
                .getSingleResult();

        System.out.println(foundedMenu);
    }


    @Test
    void 결과_타입_명시X() {
        String sql = "SELECT menu_code, menu_name FROM tbl_menu";

        List<Object[]> menuList = em.createNativeQuery(sql).getResultList();

        menuList.forEach(row -> System.out.println(Arrays.toString(row)));
    }


    @DisplayName("모든 메뉴 조회시 카테고리명도 같이 조회")
    @Test
    void 실습1() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.menu_name, c.category_name");
        sql.append("    FROM tbl_menu m");
        sql.append("    LEFT JOIN tbl_category c ON c.category_code = m.category_code");

        List<Object[]> menuList = em.createNativeQuery(sql.toString()).getResultList();

        menuList.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @DisplayName("모든 sub 카테고리 정보를 조회")
    @Test
    void 실습2() {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT category_name");
//        sql.append("    FROM tbl_category");
//        sql.append("    WHERE ref_category_code IS NOT NULL");
//
//        List<String> categoryList = em.createNativeQuery(sql.toString(), String.class).getResultList();
//
//        categoryList.forEach(System.out::println);


        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code)");
        sql.append("    FROM tbl_category c");
        sql.append("    JOIN tbl_menu m ON m.category_code = c.category_code");
        sql.append("    WHERE ref_category_code IS NOT NULL");
        sql.append("    GROUP BY c.category_code");

        List<Object[]> categoryList = em.createNativeQuery(sql.toString()).getResultList();

        categoryList.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @Test
    void 결과_매핑() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code) AS menu_count");
        sql.append("    FROM tbl_category c");
        sql.append("    JOIN tbl_menu m ON m.category_code = c.category_code");
        sql.append("    WHERE ref_category_code IS NOT NULL");
        sql.append("    GROUP BY c.category_code");

        List<Object[]> categoryList = em.createNativeQuery(sql.toString(), "categoryAndNameCountMapping1").getResultList();

        categoryList.forEach(row -> System.out.println(Arrays.toString(row)));
    }

    @Test
    void 결과_매핑_DTO() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.category_code, c.category_name, c.ref_category_code, COUNT(m.menu_code) AS menu_count");
        sql.append("    FROM tbl_category c");
        sql.append("    JOIN tbl_menu m ON m.category_code = c.category_code");
        sql.append("    WHERE ref_category_code IS NOT NULL");
        sql.append("    GROUP BY c.category_code");

        List<CategoryDto> categoryList = em.createNativeQuery(sql.toString(), "categoryAndNameCountMapping2").getResultList();

        categoryList.forEach(row -> System.out.println(row));
    }

}
