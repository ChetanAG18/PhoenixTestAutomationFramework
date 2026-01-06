package com.api.utils;

import java.util.Iterator;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.api.bean.CreateJobBean;

public class ExcelReaderUtil3 {

	public static void main(String[] args) {
		Iterator<CreateJobBean> iterator = ExcelReaderUtil2.loadTestData("testData/PhoenixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);
		
		while(iterator.hasNext()) {
			CreateJobBean bean = iterator.next();
			CreateJobPayload payload = CreateJobBeanMapper.mapper(bean);
			System.out.println(payload);
		}
	}

}
