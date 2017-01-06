package com.slv.slv_api;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.slv.pojos.SlvJsonComparator;
import com.slv.slv_api.services.JsonDiffResult;


public class Test {

	public static void main(String[] args) throws JsonProcessingException, IOException  {
		SlvJsonComparator slvJsonComparator = new SlvJsonComparator();
		String source = "{\"attribute1\":{\"1.att1\":\"valeur1\"},\"attribute2\":null,\"attribute7\":null}";
		String target = "{\"attribute3\":null,\"attribute1\":{\"1.att2\":\"valeur1\",\"1.att3\":\"valeur1\"},\"attribute1bis\":null,\"attribute2\":\"yiyi\"}";	
		JsonDiffResult jsonDiffResult = slvJsonComparator.compare(source, target);
	 System.out.println(jsonDiffResult.getErrorMessage());

	}

}
