package com.green.tonicbank.dao;

import com.green.tonicbank.model.User;

public interface UserDao {

	// ȸ������
	int join(User user) throws Exception;
	
	// ���̵� �ߺ� Ȯ��
	int idCheck(String userId) throws Exception;

}