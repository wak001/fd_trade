package com.haodaibao.fund.trade.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haodaibao.fund.trade.dao.DictMapper;
import com.haodaibao.fund.trade.domain.Dict;

@Controller
@RequestMapping("dict")
public class DictAction {
	
	private final Logger logger = LoggerFactory.getLogger(DictAction.class);
	
	@Resource
	private DictMapper dictMapper;
	
	/**
	 * 导航到账户调整页面
	 * @return
	 */
	@RequestMapping("toDict")
	//@ResponseBody
	public String toDict(HttpServletRequest request){
		Dict dict = dictMapper.selectByPrimaryKey(1l);
		System.out.println("=================");
		System.out.println(dict.getDictCode());
		request.setAttribute("msg", dict.getDictCode()+"|"+dict.getDictValue1());
		return "hello";
	}
	
	/**
	 * 导航到账户调整页面
	 * @return
	 */
	@RequestMapping("toAddDict")
	@ResponseBody
	public Map<String,Object> toAddDict(){
		Map<String,Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 导航到账户调整页面
	 * @return
	 */
	@RequestMapping("toChangeDict")
	@ResponseBody
	public Map<String,Object> toChangeDict(){
		Map<String,Object> map = new HashMap<String, Object>();
		return map;
	}
	
//	/**
//	 * 查询调整记录
//	 * @param accountAdjustBean
//	 * @return
//	 */
//	@RequestMapping("toQueryDict")
//	@ResponseBody
//	public Map<String,Object> toQueryDict(QueryDictBean queryDictBean){
//		if (StringUtils.isNotBlank(queryDictBean.getCreatedTimeStart())) {
//			queryDictBean.setCreatedTimeStart(queryDictBean
//					.getCreatedTimeStart() + " 00:00:00");
//		}
//		if (StringUtils.isNotBlank(queryDictBean.getCreatedTimeEnd())) {
//			queryDictBean.setCreatedTimeEnd(queryDictBean.getCreatedTimeEnd()
//					+ " 23:59:59");
//		}
//		logger.info("查询调整记录,数据["+JsonUtil.convertObjectToJSONString(queryDictBean)+"]");
//		PageList<QueryDictVo> list = new PageList<QueryDictVo>();
//		int pageNum = (queryDictBean.getStart()/queryDictBean.getLimit())+1;
//		int limit = queryDictBean.getLimit();
//		PageBounds pageBounds = new PageBounds(pageNum,limit);
//		try {
//			list = dictMapper.queryDict(queryDictBean, pageBounds);
//		} catch (Exception e) {
//			logger.error("账户调整查询-查询失败,数据["+JsonUtil.convertObjectToJSONString(queryDictBean)+"]",e);
//		}
//		if(list.size()>0){
//			for(QueryDictVo queryDictVo:list){
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				try {
//					if(StringUtils.isNotBlank(queryDictVo.getCreatedTime()))
//					queryDictVo.setCreatedTime(sdf.format(sdf.parse(queryDictVo.getCreatedTime())));
//					if(StringUtils.isNotBlank(queryDictVo.getModifiedTime()))
//					queryDictVo.setModifiedTime(sdf.format(sdf.parse(queryDictVo.getModifiedTime())));
//				} catch (ParseException e) {
//					logger.error("转换时间格式错误",e);
//				}
//			}
//		}
//		Map<String,Object> result = new HashMap<String,Object>();
//		result.put("rows", list);
//		result.put("total", list.getPaginator().getTotalCount());
//		return result;
//	}
//	
//	/**
//	 * 查询调整记录
//	 * @param accountAdjustBean
//	 * @return
//	 */
//	@RequestMapping("addDict")
//	@ResponseBody
//	public Map<String,Object> addDict(AddDictBean addDictBean){
//		Map<String,Object> result = new HashMap<String,Object>();
//		logger.info("查询调整记录,数据["+JsonUtil.convertObjectToJSONString(addDictBean)+"]");
//		Dict dict = new Dict();
//		dict.setCreatedTime(new Date());
//		dict.setDictCode(addDictBean.getCode());
//		dict.setDictType(addDictBean.getDictType());
//		dict.setDictValue1(addDictBean.getValue1());
//		dict.setDictValue2(addDictBean.getValue2());
//		dict.setModifiedTime(new Date());
//		try {
//			dictMapper.insert(dict);
//		} catch (Exception e) {
//			logger.error("字典表添加数据异常,数据["+JsonUtil.convertObjectToJSONString(dict)+"]");
//			result.put("retMsg", "添加数据失败");
//			return result;
//		}
//		result.put("retMsg", "添加数据成功");
//		return result;
//	}
//	
//	@RequestMapping("delDict")
//	@ResponseBody
//	public Map<String,Object> delDict(DelDictBean delDictBean){
//		Map<String,Object> map = new HashMap<String, Object>();
//		List<QueryDictVo> list = delDictBean.getList();
//		StringBuffer sb = new StringBuffer();
//		for(QueryDictVo queryDictVo:list){
//			try {
//				dictMapper.deleteByPrimaryKey(Long.valueOf(queryDictVo.getId()));
//			} catch (Exception e) {
//				sb.append("DICT_CODE为："+queryDictVo.getCode()+"的条目删除失败");
//				logger.error("删除银行信息失败,数据["+JsonUtil.convertObjectToJSONString(delDictBean)+"]");
//			}
//		}
//		if(StringUtils.isBlank(sb.toString())){
//			map.put("status", "删除成功");
//		}else{
//			map.put("status", sb.toString());
//		}
//		return map;
//	}
//	
//	/**
//	 * 查询调整记录
//	 * @param accountAdjustBean
//	 * @return
//	 */
//	@RequestMapping("doChangeDict")
//	@ResponseBody
//	public Map<String,Object> doChangeDict(AddDictBean addDictBean){
//		Map<String,Object> result = new HashMap<String,Object>();
//		logger.info("查询调整记录,数据["+JsonUtil.convertObjectToJSONString(addDictBean)+"]");
//		if(StringUtils.isBlank(addDictBean.getId())){
//			result.put("retMsg", "ID未获取");
//			return result;
//		}
//		Dict dict = new Dict();
//		dict.setId(Long.valueOf(addDictBean.getId()));
//		dict.setCreatedTime(new Date());
//		dict.setDictCode(addDictBean.getCode());
//		dict.setDictType(addDictBean.getDictType());
//		dict.setDictValue1(addDictBean.getValue1());
//		dict.setDictValue2(addDictBean.getValue2());
//		dict.setModifiedTime(new Date());
//		try {
//			dictMapper.updateByPrimaryKey(dict);
//		} catch (Exception e) {
//			logger.error("字典表更新数据异常,数据["+JsonUtil.convertObjectToJSONString(dict)+"]");
//			result.put("retMsg", "更新字典表失败");
//			return result;
//		}
//		result.put("retMsg", "更新字典表数据成功");
//		return result;
//	}
}
