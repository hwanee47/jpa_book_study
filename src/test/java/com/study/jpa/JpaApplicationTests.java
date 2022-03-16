package com.study.jpa;

import com.study.jpa.model.entity.Member;
import com.study.jpa.model.entity.Team;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class JpaApplicationTests {

//	@PersistenceUnit
//	EntityManagerFactory emf;

	@Autowired
	EntityManager em;

	@BeforeEach
	public void beforeTest() {

	}

	@Test
	void contextLoads() {

	}

 	@Test
	@Rollback(value = false)
 	public void testSave() throws Exception {


		queryLogicJoin();
 	}


	@Test
	public void queryLogicJoin(){

		String jpql = "select m from MEMBER m join m.team t where "+
				"t.name=:teamName";

		List<Member> resultList = em.createQuery(jpql)
				.setParameter("teamName", "팀1")
				.getResultList();

		for(Member member : resultList){
			System.out.println("[query] member.username=" + member.getName());
		}

	}
	

	@Test
	@Rollback(value = false)
	public void 팀에서멤버객체탐색(){

		// 팀1 저장
		Team team1 = new Team("team1", "팀1");
		em.persist(team1);

		// 회원1 저장
		Member member1 = new Member("회원1");


		// 회원2 저장
		Member member2 = new Member("회원2");

		member1.setTeam(team1);
		em.persist(member1);

		member2.setTeam(team1);
		em.persist(member2);

		List<Member> members = team1.getMembers();
		members.stream().forEach(member -> {System.out.println(member.getName());});


	}


	@Test
	public void 삭제되지않은연관관계(){

		Team team1 = new Team("team1", "팀1");
		Team team2 = new Team("team2", "팀2");

		// 회원1 저장
		Member member1 = new Member("회원1");

		member1.setTeam(team1); // member1 <-> team1, 양방향 연관관계
		member1.setTeam(team2); // member1 <-> team2, 양방향 연관관계

		// 위의 코드는 team1 -> member1 연관관계가 삭제되지 않는다.
		// setTeam 메서드에서 기존의 연관관계를 끊어주는 소스코드가 필요하다.

	}
}
