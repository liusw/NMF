package com.boyaa.mf.service.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boyaa.mf.entity.config.FeedConf;
import com.boyaa.mf.entity.config.FeedPlat;
import com.boyaa.mf.mapper.config.FeedConfMapper;
import com.boyaa.mf.mapper.config.FeedPlatMapper;
import com.boyaa.mf.service.AbstractService;

/**
 *<p>Title: Feed信息配置业务服务类<p>
 *<p>Description: null</p>
 *<p>Company: boyaa</p>
 *<p>Date: May 6, 2015</p>
 * @author Joakun Chen
 */
@Service
public class FeedConfService extends AbstractService<FeedConf, Integer> {
	private static Logger logger = Logger.getLogger(FeedConfService.class);
	
	@Autowired
	private FeedConfMapper feedConfMapper;
	@Autowired
	private FeedPlatMapper feedPlatMapper;

	@Override
	public int insert(FeedConf feed) {
		/*检查是否已存在相同id的记录，存在则不添加*/
		FeedConf existFeed = findById(feed.getId());
		if(null == existFeed){
			return super.insert(feed);
		}
		
		logger.warn("Has existed the same id record.");
		return 0;
	}
	
	@Override
	public int update(FeedConf feed) {
		int updatePlatFeedRst = -1;
		
		/*检查是否已存在相同id的记录，存在则不添加*/
		FeedConf existFeed = findById(feed.getId());
		if(null != existFeed){
			int upateFeedRst = feedConfMapper.update(feed);
			
			if(upateFeedRst > 0){
				 /**Feed名称更新时，需要更新平台Feed中的Feed名称  feedPlat包含feedId,feedName*/
				FeedPlat feedPlat = new FeedPlat();
				feedPlat.setFeedId(feed.getId());
				feedPlat.setFeedName(feed.getName());
				
				updatePlatFeedRst = feedPlatMapper.updateFeedName(feedPlat);

				logger.info("更新" + updatePlatFeedRst + "个平台Feed表中的Feed（" + feed.getId() + "）名称为: " + feed.getName());
			}
			
			return updatePlatFeedRst>=0?1:updatePlatFeedRst;
		}
		
		logger.warn("Can not update because the record(id:" + feed.getId()+ ") not exist.");
		return 0;
	}
}
