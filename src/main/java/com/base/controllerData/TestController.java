package com.base.controllerData;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/test")
public class TestController extends BaseController {
	
	@Autowired
	private TestService testService;
	
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public void query() {
		
		PageData pd = this.getPageData();
		
		String name = pd.getString("name");
		int type = pd.getInt("type");
		boolean isOk = pd.getBoolean("isOk");
		double doublee = pd.getDouble("doublee");
		
		List<PageData> list = testService.query(pd);
	}
	
}
