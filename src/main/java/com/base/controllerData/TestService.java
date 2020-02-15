package com.base.controllerData;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class TestService {

	@Autowired
	private BaseDao dao;

	
	public List<PageData> query(PageData pd) {
		return (List<PageData>) dao.findForList("TestMapper.query", pd);
	}

}
