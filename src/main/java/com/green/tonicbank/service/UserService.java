package com.green.tonicbank.service;

import com.green.tonicbank.model.User;

public interface UserService {

	// ȸ������
	int join(User user) throws Exception;
	
	//���̵� �ߺ� Ȯ��
	int idCheck(String userId) throws Exception;

}