package com.bet.actions.admin.ajax;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bet.actions.admin.BaseAdminController;
import com.bet.daos.IWithdrawInfoDao;
import com.bet.enums.ApplyStatus;
import com.bet.orms.AdminInfo;
import com.bet.orms.WithdrawInfo;
import com.bet.services.CWithdrawService;
import com.bet.services.IWithdrawInfoService;
import com.bet.utils.BetConstValues;
import com.lrcall.common.exceptions.HibernateJsonResultException;
import com.lrcall.common.models.ErrorInfo;
import com.lrcall.common.models.ReturnInfo;
import com.lrcall.common.models.TableData;
import com.lrcall.common.models.TableOrderInfo;
import com.lrcall.common.models.TableSearchInfo;
import com.lrcall.common.utils.StringTools;
import com.lrcall.common.utils.TimeTools;
import com.lrcall.lrweb.common.enums.SqlOrderType;
import com.lrcall.lrweb.common.utils.TableTools;

@Controller("adminWithdrawInfoAction")
@RequestMapping(value = "/admin")
public class WithdrawInfoAction extends BaseAdminController
{
	@Autowired
	private IWithdrawInfoService withdrawInfoService;
	@Autowired
	private CWithdrawService cWithdrawService;

	/**
	 * 更新用户提现支付信息接口<br>
	 * 
	 * @param request
	 * @param withdrawInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithdrawInfoVerify", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateWithdrawInfoVerify(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId,
		@RequestParam(value = "remark", required = false) String remark, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			if (status != ApplyStatus.VERIFY_SUCCESS.getStatus() && status != ApplyStatus.VERIFY_FAIL.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "审核状态错误！");
			}
			return cWithdrawService.updateWidthdrawInfoVerify(withdrawId, remark, status);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户提现支付信息接口<br>
	 * 
	 * @param request
	 * @param withdrawInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithdrawInfoVerifyBat", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateWithdrawInfoVerifyBat(HttpServletRequest request, @RequestParam(value = "withdrawIds", required = true) String withdrawIds,
		@RequestParam(value = "remark", required = false) String remark, @RequestParam(value = "status", required = true) byte status)
	{
		try
		{
			if (status != ApplyStatus.VERIFY_SUCCESS.getStatus() && status != ApplyStatus.VERIFY_FAIL.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "审核状态错误！");
			}
			String[] withdrawIdList = withdrawIds.split(",");
			int success = 0, fail = 0;
			for (String withdrawId : withdrawIdList)
			{
				try
				{
					ReturnInfo returnInfo = cWithdrawService.updateWidthdrawInfoVerify(withdrawId, remark, status);
					if (ReturnInfo.isSuccess(returnInfo))
					{
						success++;
					}
					else
					{
						fail++;
					}
				}
				catch (HibernateJsonResultException e)
				{
					toExceptionReturnInfo(e);
					fail++;
				}
			}
			return toStringReturnInfo(String.format("成功%d个，失败%d个。", success, fail));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户提现支付信息接口<br>
	 * 
	 * @param request
	 * @param withdrawInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithdrawInfoPay", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateWithdrawInfoPay(HttpServletRequest request, @ModelAttribute("withdrawInfo") WithdrawInfo withdrawInfo)
	{
		try
		{
			AdminInfo sessionAdminInfo = getAdminSession(request);
			long current = System.currentTimeMillis();
			Map<String, Object> valueMap = new HashMap<>();
			valueMap.put(IWithdrawInfoDao.PAY_USER_ID, sessionAdminInfo.getUserId());
			valueMap.put(IWithdrawInfoDao.PAY_PIC_URL, withdrawInfo.getPayPicUrl());
			valueMap.put(IWithdrawInfoDao.PAY_REMARK, withdrawInfo.getPayRemark());
			valueMap.put(IWithdrawInfoDao.STATUS, ApplyStatus.PAYED.getStatus());
			valueMap.put(IWithdrawInfoDao.PAY_DATE_LONG, current);
			valueMap.put(IWithdrawInfoDao.UPDATE_DATE_LONG, current);
			if (!withdrawInfoService.updateValueByWithdrawId(withdrawInfo.getWithdrawId(), valueMap))
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "更新用户提现支付信息失败！");
			}
			return toStringReturnInfo("更新用户提现支付信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户已收款接口<br>
	 * 
	 * @param request
	 * @param withdrawInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithdrawInfoProcessed", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateWithdrawInfoProcessed(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			return cWithdrawService.updateWidthdrawInfoProcessed(withdrawId);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	/**
	 * 更新用户已收款接口<br>
	 * 
	 * @param request
	 * @param withdrawInfo
	 * @return
	 */
	@RequestMapping(value = "/ajaxUpdateWithdrawInfoProcessedBat", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxUpdateWithdrawInfoProcessedBat(HttpServletRequest request, @RequestParam(value = "withdrawIds", required = true) String withdrawIds)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			String[] withdrawIdList = withdrawIds.split(",");
			int success = 0, fail = 0;
			for (String withdrawId : withdrawIdList)
			{
				try
				{
					ReturnInfo returnInfo = cWithdrawService.updateWidthdrawInfoProcessed(withdrawId);
					if (ReturnInfo.isSuccess(returnInfo))
					{
						success++;
					}
					else
					{
						fail++;
					}
				}
				catch (HibernateJsonResultException e)
				{
					toExceptionReturnInfo(e);
					fail++;
				}
			}
			return toStringReturnInfo(String.format("成功%d个，失败%d个。", success, fail));
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxDeleteWithdrawInfo", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo ajaxDeleteWithdrawInfo(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
			if (withdrawInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户提现信息不存在！");
			}
			if (withdrawInfo.getStatus() != ApplyStatus.APPLY.getStatus())
			{
				throw new HibernateJsonResultException(ErrorInfo.FORBIDDEN_ERROR, "提现状态不是新申请的不能删除！");
			}
			withdrawInfoService.deleteWithdrawInfoByWithdrawId(withdrawId);
			return toStringReturnInfo("删除用户提现信息成功！");
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetWithdrawInfo")
	@ResponseBody
	public ReturnInfo ajaxGetWithdrawInfo(HttpServletRequest request, @RequestParam(value = "withdrawId", required = true) String withdrawId)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			WithdrawInfo withdrawInfo = withdrawInfoService.getWithdrawInfoByWithdrawId(withdrawId);
			if (withdrawInfo == null)
			{
				throw new HibernateJsonResultException(ErrorInfo.HIBERNATE_ERROR, "用户提现信息不存在！");
			}
			return toObjectReturnInfo(withdrawInfo);
		}
		catch (HibernateJsonResultException e)
		{
			return toExceptionReturnInfo(e);
		}
	}

	@RequestMapping(value = "/ajaxGetWithdrawInfoList")
	@ResponseBody
	public TableData ajaxGetWithdrawInfoList(HttpServletRequest request)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			Map<String, Object> tableMap = TableTools.getTableParams(request);
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			buildTable(request, tableMap, orderInfos, searchInfos,
				new String[] { IWithdrawInfoDao.USER_ID, IWithdrawInfoDao.PAYEE_NAME, IWithdrawInfoDao.BANK_NAME, IWithdrawInfoDao.BANK_CARD_ID, IWithdrawInfoDao.STATUS });
			List<WithdrawInfo> list = withdrawInfoService.getWithdrawInfoList((int) tableMap.get(TableTools.START), (int) tableMap.get(TableTools.LENGTH), orderInfos, searchInfos);
			long count = withdrawInfoService.getWithdrawInfoListCount(searchInfos);
			TableData tableData = TableTools.getTableDataInfo((int) tableMap.get(TableTools.DRAW), (int) tableMap.get(TableTools.START), count, list);
			return tableData;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取提现总金额接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetUserWithdrawTotalMoney")
	@ResponseBody
	public long ajaxGetUserWithdrawTotalMoney(HttpServletRequest request, @RequestParam(value = "userId", required = false) String userId,
		@RequestParam(value = "startDateLong", required = false) Long startDateLong, @RequestParam(value = "endDateLong", required = false) Long endDateLong)
	{
		try
		{
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IWithdrawInfoDao.STATUS, ApplyStatus.PROCESSED.getStatus() + "", true, false));
			if (!StringTools.isNull(userId))
			{
				searchInfos.add(new TableSearchInfo(IWithdrawInfoDao.USER_ID, userId, true, false));
			}
			long money = withdrawInfoService.getSum(IWithdrawInfoDao.MONEY, searchInfos, startDateLong, endDateLong);
			return money;
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
		}
		return 0;
	}

	/**
	 * 导出提现的信息到Excel接口<br>
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportWithdrawToExcel")
	public ModelAndView exportWithdrawToExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "count", required = false) Integer count)
	{
		try
		{
			// AdminInfo sessionAdminInfo = getAdminSession(request);
			// 创建excel工作簿
			Workbook wb = new HSSFWorkbook();
			// 创建第一个sheet（页），并命名
			Sheet sheet = wb.createSheet(BetConstValues.PROJECT_NAME + "用户提现");
			// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
			sheet.setColumnWidth((short) 0, (short) (40 * 150));
			sheet.setColumnWidth((short) 1, (short) (40 * 150));
			sheet.setColumnWidth((short) 2, (short) (40 * 150));
			sheet.setColumnWidth((short) 3, (short) (40 * 150));
			sheet.setColumnWidth((short) 4, (short) (40 * 150));
			sheet.setColumnWidth((short) 5, (short) (40 * 150));
			// 创建第一行
			Row row = sheet.createRow((short) 0);
			// 创建两种单元格格式
			CellStyle cs = wb.createCellStyle();
			CellStyle cs2 = wb.createCellStyle();
			// DataFormat df = wb.createDataFormat();
			// 创建两种字体
			Font f = wb.createFont();
			Font f2 = wb.createFont();
			// 创建第一种字体样式
			f.setFontHeightInPoints((short) 10);
			f.setColor(IndexedColors.RED.getIndex());
			f.setBold(true);
			// 创建第二种字体样式
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.BLACK.getIndex());
			f.setBold(true);
			// 设置第一种单元格的样式
			cs.setFont(f);
			cs.setBorderLeft(BorderStyle.THIN);
			cs.setBorderRight(BorderStyle.THIN);
			cs.setBorderTop(BorderStyle.THIN);
			cs.setBorderBottom(BorderStyle.THIN);
			// cs.setDataFormat(df.getFormat("#,##0.0"));
			// 设置第二种单元格的样式
			cs2.setFont(f2);
			cs2.setBorderLeft(BorderStyle.THIN);
			cs2.setBorderRight(BorderStyle.THIN);
			cs2.setBorderTop(BorderStyle.THIN);
			cs2.setBorderBottom(BorderStyle.THIN);
			// cs2.setDataFormat(df.getFormat("text"));
			// 创建列（每行里的单元格）
			Cell cell = row.createCell(0);
			cell.setCellValue("提现ID");
			cell.setCellStyle(cs);
			cell = row.createCell(1);
			cell.setCellValue("收款银行");
			cell.setCellStyle(cs);
			cell = row.createCell(2);
			cell.setCellValue("收款卡号");
			cell.setCellStyle(cs);
			cell = row.createCell(3);
			cell.setCellValue("收款人");
			cell.setCellStyle(cs);
			cell = row.createCell(4);
			cell.setCellValue("转账金额");
			cell.setCellStyle(cs);
			cell = row.createCell(5);
			cell.setCellValue("审核时间");
			cell.setCellStyle(cs);
			cell = row.createCell(6);
			cell.setCellValue("备注");
			cell.setCellStyle(cs);
			int start = 0;
			int size = 100;
			int i = 0;
			List<TableOrderInfo> orderInfos = new ArrayList<>();
			orderInfos.add(new TableOrderInfo(IWithdrawInfoDao.ADD_DATE_LONG, SqlOrderType.DESC.getType()));
			List<TableSearchInfo> searchInfos = new ArrayList<>();
			searchInfos.add(new TableSearchInfo(IWithdrawInfoDao.STATUS, ApplyStatus.VERIFY_SUCCESS.getStatus() + "", false, false));
			boolean bGet = true;
			List<WithdrawInfo> list = null;
			while (bGet)
			{
				list = withdrawInfoService.getWithdrawInfoList(start, size, orderInfos, searchInfos);
				if (list == null || list.size() < 1)
				{
					break;
				}
				for (WithdrawInfo withdrawInfo : list)
				{
					try
					{
						// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
						// 创建一行，在页sheet上
						row = sheet.createRow(i + 1);
						// 在row行上创建一个方格
						cell = row.createCell(0);
						cell.setCellValue(withdrawInfo.getWithdrawId());
						cell.setCellStyle(cs2);
						cell = row.createCell(1);
						cell.setCellValue(withdrawInfo.getBankName());
						cell.setCellStyle(cs2);
						cell = row.createCell(2);
						cell.setCellValue(withdrawInfo.getBankCardId());
						cell.setCellStyle(cs2);
						cell = row.createCell(3);
						cell.setCellValue(withdrawInfo.getPayeeName());
						cell.setCellStyle(cs2);
						cell = row.createCell(4);
						String agentUserId = String.format("%.2f元", (double) withdrawInfo.getUserMoney() / 100);
						if (StringTools.isNull(agentUserId))
						{
							agentUserId = "";
						}
						cell.setCellValue(agentUserId);
						cell.setCellStyle(cs2);
						cell = row.createCell(5);
						Long verifyDateLong = withdrawInfo.getVerifyDateLong();
						String verifyDate = "";
						if (verifyDateLong != null)
						{
							verifyDate = TimeTools.getDateTimeString(verifyDateLong);
						}
						cell.setCellValue(verifyDate);
						cell.setCellStyle(cs2);
						cell = row.createCell(6);
						cell.setCellValue(withdrawInfo.getRemark());
						cell.setCellStyle(cs2);
						i++;
						if (count != null && count > 0 && i >= count)
						{
							bGet = false;
							break;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (list.size() < size)
				{
					break;
				}
				else
				{
					start += size;
				}
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			try
			{
				wb.write(os);
				wb.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=dm-withdraw-" + TimeTools.getTodayDateLong() + ".xls");
			ServletOutputStream out;
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try
			{
				out = response.getOutputStream();
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(out);
				byte[] buff = new byte[2048];
				int bytesRead;
				// Simple read/write loop.
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
				{
					bos.write(buff, 0, bytesRead);
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (bis != null) bis.close();
					if (bos != null) bos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (HibernateJsonResultException e)
		{
			toExceptionReturnInfo(e);
		}
		return null;
	}
}
