package com.bet.daos.mysqlimpls;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bet.daos.IPicInfoDao;
import com.bet.orms.PicInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.mysqlimpls.IAbstractDaoImpl;
import com.lrcall.lrweb.common.utils.StringTools;

@Repository("picInfoDao")
public class IPicInfoDaoMySqlImpl extends IAbstractDaoImpl<PicInfo> implements IPicInfoDao
{
	@Override
	public ReturnInfo checkParams(PicInfo picInfo)
	{
		if (picInfo == null)
		{
			return new ReturnInfo(ErrorInfo.PARAM_ERROR, "PicInfo为空！");
		}
		ReturnInfo returnInfo = StringTools.checkParams(
			new String[] { picInfo.getPicUrl(), picInfo.getStatus() + "", picInfo.getHeight() + "", picInfo.getWidth() + "", picInfo.getPicId(), picInfo.getSortId() },
			new String[] { "picUrl不能为空！", "status不能为空！", "height不能为空！", "width不能为空！", "picId不能为空！", "sortId不能为空！" });
		return returnInfo;
	}

	@Override
	public PicInfo add(PicInfo picInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(picInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.add(picInfo);
	}

	@Override
	public PicInfo update(PicInfo picInfo) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(picInfo);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.update(picInfo);
	}

	@Override
	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap)
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		return super.updateValue(PicInfo.class, valueMap, whereMap);
	}

	@Override
	public void deletePicInfoByPicId(String picId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { picId }, new String[] { "picId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		PicInfo picInfo = getPicInfoByPicId(picId);
		if (picInfo == null)
		{
			throw new HibernateJsonResultException(ErrorInfo.NOT_EXIST_ERROR, "PicInfo不存在！");
		}
		delete(picInfo);
	}

	@Override
	public PicInfo getPicInfoByPicId(String picId) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = StringTools.checkParams(new String[] { picId }, new String[] { "picId不能为空！" });
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		String hql = String.format("from %s where %s = :%s", PicInfo.class.getSimpleName(), IPicInfoDao.PIC_ID, IPicInfoDao.PIC_ID);
		Map<String, Object> params = new HashMap<>();
		params.put(IPicInfoDao.PIC_ID, picId);
		return baseDao.hqlQuery(hql, params);
	}

	@Override
	public long updateValueByPicId(String picId, Map<String, Object> valueMap) throws HibernateJsonResultException
	{
		ReturnInfo returnInfo = checkParams(valueMap);
		if (returnInfo != null)
		{
			throw new HibernateJsonResultException(returnInfo);
		}
		Map<String, Object> whereMap = new HashMap<>();
		whereMap.put(IPicInfoDao.PIC_ID, picId);
		return updateValue(PicInfo.class, valueMap, whereMap);
	}

	@Override
	public ReturnInfo checkParams(Map<String, Object> valueMap)
	{
		return null;
	}
	/* 自定义方法 */
}
