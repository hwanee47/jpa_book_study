package com.study.jpa;

import com.study.jpa.model.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

@SpringBootTest
class JpaApplicationTests {

	@PersistenceUnit
	EntityManagerFactory emf;

	@Test
	void contextLoads() {
	}

 	@Test
 	public void JPA_테스트() throws Exception {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Member member = new Member();
			member.setId("112");
			member.setUsername("ggg");

			// 영속성상태
			// JPA는 영속성컨텍스트 저장해두었다가 commit되기전 flush가 호출되어 insert쿼리가 실행된다
			em.persist(member);

			tx.commit();
		}catch(Exception e){
			tx.rollback();
		}
 	}
	

}
