package com.sotogito.section02.projection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

public class ProjectionJPQLTest {

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
    void 엔티티_프로젝션() {
        String jqpl = "SELECT m FROM menu2 m WHERE m.menuCode = :menuCode";

        Menu foundMenu = em.createQuery(jqpl, Menu.class)
                .setParameter("menuCode", 3)
                .getSingleResult();

        System.out.println(foundMenu);
        System.out.println(em.contains(foundMenu)); ///영속상태인가?

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            foundMenu.setOrderableStatus("N");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Test
    void 임베디드_select() {
        String jqpl = "SELECT m.menuInfo FROM menu2 m WHERE m.menuCode = :menuCode";
//        String jqplEm = "SELECT m FROM menu2 m WHERE m.menuInfo.menuName = '열무김치라떼'";

        MenuInfo menuInfo = em.createQuery(jqpl, MenuInfo.class)
                .setParameter("menuCode", 5)
                .getSingleResult();

        System.out.println(menuInfo);
        System.out.println(menuInfo.getMenuName());
        System.out.println(menuInfo.getMenuPrice());
    }

    @Test
    void 스칼라_프로젝션_테스트() {
        String jqpl= "SELECT c.categoryName FROM category2 c";

        List<String> categoryNameList = em.createQuery(jqpl, String.class)
                .getResultList();

        System.out.println(categoryNameList);
    }

    @Test
    void 스칼라_프로젝션_테스트2() {
        String jqpl= "SELECT c.categoryCode, c.categoryName FROM category2 c"; // 다중열로 조회시 타입을 지정할 수 없음


        List<Object[]> result= em.createQuery(jqpl).getResultList();

        for (Object[] objects : result) {
            int categoryCode = (Integer) objects[0];
            String categoryName = (String) objects[1];

            System.out.printf("categoryCode=%d, categoryName=%s\n", categoryCode, categoryName);
        }
    }

    @Test
    void DTO() {
        String jqpl = "SELECT new com.sotogito.section02.projection.CategoryResponseDto(c.categoryCode, c.categoryName) FROM category2 c";

        List<CategoryResponseDto> result = em.createQuery(jqpl, CategoryResponseDto.class).getResultList();

        result.stream().forEach(System.out::println);
    }



}
