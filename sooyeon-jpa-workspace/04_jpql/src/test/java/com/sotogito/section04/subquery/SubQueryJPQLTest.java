package com.sotogito.section04.subquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

public class SubQueryJPQLTest {

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


    @Test
    void 서브쿼리() {
//        String spql = """
//                SELECT m
//                FROM menu4 m
//                WHERE m.category.categoryCode = (SELECT c.categoryCode
//                                        FROM category4 c
//                                        WHERE c.categoryName = :categoryName)
//                """;

        String spql = """
                SELECT m
                FROM menu4 m
                WHERE m.category.categoryName = :categoryName
                """;

        List<Menu> menuList = em.createQuery(spql, Menu.class)
                .setParameter("categoryName", "한식")
                .getResultList();

        menuList.forEach(System.out::println);
    }

    @Test
    void 동적쿼리() {
        /**
         * 메뉴명 키워드와 카테고리 코드로 필터링 검색 기능이 있다고 가정
         * 단, 키워드와 카테고리 코드는 입력될 수도 있고 안될 수도 있다고 가정
         */
        StringBuilder jpql = new StringBuilder("SELECT m FROM menu4 m JOIN FETCH m.category");

        String searchKeyword = "김치";
        int searchCategoryCode = 8;

        boolean containSearchKeyword = searchKeyword != null && !"".equals(searchKeyword);
        boolean containSearchCategoryCode = searchCategoryCode != 0;

        if(containSearchKeyword) {
            jpql.append(" WHERE m.menuName LIKE '%' || :keyword || '%'");
        }
        if(containSearchCategoryCode) {
            jpql.append(" AND m.category.categoryCode = :categoryCode");
        }


        TypedQuery<Menu> query = em.createQuery(jpql.toString(), Menu.class);
        if(containSearchKeyword) {
            query.setParameter("keyword", searchKeyword);
        }
        if(containSearchCategoryCode) {
            query.setParameter("categoryCode", searchCategoryCode);
        }


        List<Menu> menuList = query.getResultList();
        menuList.forEach(System.out::println);

    }

}
