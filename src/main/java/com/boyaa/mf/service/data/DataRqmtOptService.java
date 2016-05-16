package com.boyaa.mf.service.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.data.DataRqmtOpt;
import com.boyaa.mf.service.AbstractService;

@Service
public class DataRqmtOptService extends AbstractService<DataRqmtOpt, Integer> {
	@Override
	public int insert(DataRqmtOpt dataRqmtOpt) {
		String content = dataRqmtOpt.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
		dataRqmtOpt.setContent(content);
		return super.insert(dataRqmtOpt);
	}

	@Override
	public int update(DataRqmtOpt dataRqmtOpt) {
		String content = dataRqmtOpt.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
		dataRqmtOpt.setContent(content);
		return super.update(dataRqmtOpt);
	}

	public DataRqmtOpt findById(int entityId) {
		DataRqmtOpt dataRqmtOpt = super.findById(entityId);
		String content = dataRqmtOpt.getContent().replaceAll(Constants.UPLOAD_IMAGES_PREFIX, Constants.UPLOAD_IMAGES_DOMAIN);
		dataRqmtOpt.setContent(content);
		return dataRqmtOpt;
	}

	public List<DataRqmtOpt> findDataRqmtOptList(Map<String, Object> params){
		List<DataRqmtOpt> dataRqmtOpts = findScrollDataList(params);
		if(dataRqmtOpts != null){
			for(DataRqmtOpt dataRqmtOpt : dataRqmtOpts){
				String content = dataRqmtOpt.getContent().replaceAll(Constants.UPLOAD_IMAGES_PREFIX, Constants.UPLOAD_IMAGES_DOMAIN);
				dataRqmtOpt.setContent(content);
			}
		}
		return dataRqmtOpts;
	}
}
