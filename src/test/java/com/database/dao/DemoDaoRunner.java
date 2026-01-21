package com.database.dao;

import com.database.model.JobHeadModel;

public class DemoDaoRunner {

	public static void main(String[] args) {
		JobHeadModel jobHeadModel = JobHeadDao.getDataFromJobHead(162870);
		System.out.println(jobHeadModel);
	}

}
