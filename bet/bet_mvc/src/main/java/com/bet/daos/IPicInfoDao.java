package com.bet.daos;

import java.util.Map;

import com.bet.orms.PicInfo;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.daos.IAbstractDao;

public interface IPicInfoDao extends IAbstractDao<PicInfo>
{
	public static final String ID = "id";
	public static final String PIC_ID = "picId";
	public static final String SORT_ID = "sortId";
	public static final String PIC_URL = "picUrl";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	public static final String PIC_SIZE_INFO = "picSizeInfo";
	public static final String STATUS = "status";

	public void deletePicInfoByPicId(String picId) throws HibernateJsonResultException;

	public PicInfo getPicInfoByPicId(String picId) throws HibernateJsonResultException;

	public long updateValueByPicId(String picId, Map<String, Object> valueMap) throws HibernateJsonResultException;

	public long updateValue(Map<String, Object> valueMap, Map<String, Object> whereMap);

	public ReturnInfo checkParams(Map<String, Object> valueMap);
	/* 自定义方法 */
}
