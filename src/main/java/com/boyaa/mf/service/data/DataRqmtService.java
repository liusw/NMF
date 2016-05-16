package com.boyaa.mf.service.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.data.DataRqmt;
import com.boyaa.mf.service.AbstractService;

@Service
public class DataRqmtService extends AbstractService<DataRqmt, Integer> {
	@Override
	public int insert(DataRqmt dataRqmt) {
		String content = dataRqmt.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
		dataRqmt.setContent(content);
		return super.insert(dataRqmt);
	}

	@Override
	public int update(DataRqmt dataRqmt) {
		if(StringUtils.isNotBlank(dataRqmt.getContent())){
			String content = dataRqmt.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
			dataRqmt.setContent(content);
		}
		return super.update(dataRqmt);
	}

	public DataRqmt findById(int id) {
		DataRqmt dataRqmt = super.findById(id);
		String content = dataRqmt.getContent().replaceAll(Constants.UPLOAD_IMAGES_PREFIX, Constants.UPLOAD_IMAGES_DOMAIN);
		dataRqmt.setContent(content);
		return dataRqmt;
	}

	public List<DataRqmt> findDataRqmtList(){
		Map<String, Object> params = new HashMap<String, Object>();
		List<DataRqmt> dataRqmts = findScrollDataList(params);
		return dataRqmts;
	}
}
