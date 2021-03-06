package fin.prj.miri.member;

public interface MemberService {
	int insert(MemberVO user);
	MemberVO login(MemberVO loginUser);
	boolean idCheck(String member_id);
	
	int memberInsert(String member_id, String member_pass, String member_family);
	MemberVO memberSelect(String member_id, String member_pass); 
	
	MemberVO stateSelect(String member_id);
	int stateUpdate(String member_id, String member_car_state);
}

