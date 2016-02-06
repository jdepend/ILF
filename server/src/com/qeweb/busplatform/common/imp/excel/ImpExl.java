package com.qeweb.busplatform.common.imp.excel;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;

import com.qeweb.busplatform.common.imp.ImportIA;
import com.qeweb.framework.bc.BusinessObject;
import com.qeweb.framework.bc.sysbop.FileBOP;
import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.PropertyUtils;
import com.qeweb.framework.common.utils.excel.ExcelUtil;
import com.qeweb.framework.exception.BOException;
import com.qeweb.framework.exception.QewebException;

/**
 * 业务平台excel导入通用结构.
 * <br>
 * excel是由模板管理功能自动生成的, 在模板列头信息中需要包含批注, 批注内容是BO的属性名.
 */
abstract public class ImpExl extends BusinessObject implements ImportIA {

	private static final long serialVersionUID = -8377553415019154807L;

	private FileBOP exlFile;

	/**
	 * 导入操作, 一般情况下, 导入功能仅需要实现saveData(boList) 和 getTargetBO();
	 * 如果批注中含有bo1.bo2.bop或bo.bop的格式, 还需覆写setValue方法.
	 * <ul>imp是模板方法:
	 * <li>1.校验待导入文件是否存在(isExlxists());
	 * <li>2.excel是否有数据(isDataExists());
	 * <li>3.获取批注信息,并判断批注是否正确(getExlFormat());
	 * <li>4.读取(readData)数据并导入(impData)数据, impData是模板方法, 每个sheet页执行一次保存(saveData).
	 * </ul>
	 *
	 * @param boList
	 * @throws Exception
	 */
	@Override
	final public void imp() throws BOException {
		if(!isExlExists())
			throw new BOException("文件不存在！");

		Sheet[] sheets = getSheets();
		if(!isDataExists(sheets))
			throw new BOException("无导入数据！");

		List<String> exlFormat = getExlFormat(sheets);
		readData(sheets, exlFormat);
	}

	/**
	 * 获取excel批注, 如果批注信息有误, 抛出BOException.
	 * 批注及列头信息以第一个sheet页为准.
	 * 批注从第一行第二列开始.
	 * <ul>以下条件满足任意一个则表示批注有误:
	 * 	<li>1. 列头没有批注;
	 * 	<li>2. 用户自定义校验(对于批注的校验)
	 * @return 批注信息List
	 * @throws BOException
	 */
	final public List<String> getExlFormat(Sheet[] sheets) throws BOException {
		List<String> exlFormat = new LinkedList<String>();
		int row = 0;
		int cols = sheets[0].getColumns();

		for (int c = 1; c < cols; c++) {
			Cell cell = sheets[0].getCell(c, row);
			exlFormat.add(cell.getCellFeatures().getComment());
		}

		if(ContainerUtil.isNull(exlFormat))
			throw new BOException("Excel格式有误：不存在批注信息！");
		
		if(!validateExlFormat(exlFormat))
			return null;
		
		return exlFormat;
	}

	/**
	 * 读取数据, 从第二行第二列开始读取.
	 * <ul>excel sheet页的格式:
	 * <li>第一行, 带有批注的标题列;
	 * <li>第二行开始, 要导入的数据;
	 * <li>第一列, 序号列, 无需导入;
	 * <li>第二列开始, 待导入的属性.
	 * @param sheets
	 * @param exlFormat 批注信息
	 * @throws BOException
	 */
	public void readData(Sheet[] sheets, List<String> exlFormat) throws BOException {
		for (int i = 0; i < sheets.length; i++) {
			List<List<String>> sheetData = new LinkedList<List<String>>();
			int rows = sheets[i].getRows();
			int cols = sheets[i].getColumns();
			for(int r = 1; r < rows; r++){
				List<String> rowData = new LinkedList<String>();
				for (int c = 1; c < cols; c++) {
					Cell cell = sheets[i].getCell(c, r);
					rowData.add(cell.getContents());
				}
				sheetData.add(rowData);
			}

			impData(sheetData, exlFormat, i);
			sheetData.clear();
		}
	}

	/**
	 * 导入数据, 如果导入数据有误, 抛出BOException.
	 * @param sheetData excel的sheet页中的数据
	 * @param exlFormat 批注信息
	 * @param sheetNO sheet编号
	 * @throws BOException
	 */
	protected void impData(List<List<String>> sheetData, List<String> exlFormat, int sheetNO) throws BOException {
		//行号,列号
		int rowNO = 0, colNO = 0;
		List<BusinessObject> boList = new LinkedList<BusinessObject>();
		try {
			while(rowNO < sheetData.size()) {
				List<String> rowData = sheetData.get(rowNO);
				BusinessObject bo = getTargetBO();

				Map<String, String> valueMap = new HashMap<String, String>();
				while(colNO < exlFormat.size()) {
					valueMap.put(exlFormat.get(colNO), rowData.get(colNO));
					colNO++;
				}

				setValue(bo, valueMap);
				boList.add(bo);
				rowNO++;
				colNO = 0;
			}
			saveData(boList);
		} catch(BOException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException("第" + rowNO + "行 第" + colNO + "列数据类型转换错误！");
		} finally {
			for(BusinessObject bo : boList) {
				bo.free();
			}
			boList.clear();
		}
	}

	/**
	 * 为BO的fieldName属性赋值, 其子类可根据需要覆盖该方法
	 * @param bo
	 * @param valueMap	key:属性名,  value:属性对应的值
	 * @throws Exception
	 */
	protected void setValue(BusinessObject bo, Map<String, String> valueMap) throws Exception {
		for(Entry<String, String> entry : valueMap.entrySet()) {
			PropertyUtils.setStrPropertyThrowsException(bo, entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * 自定义批注校验
	 * @param exlFormat  批注信息
	 * @return
	 */
	protected boolean validateExlFormat(List<String> exlFormat) throws BOException {
		return true;
	}
	
	/**
	 * excel导入的参考列.
	 * <br>
	 * getReferenceColumn返回一个String泛型的List, 每个元素记录一个excel的批注名称, 意思是按照这些批注合并行信息.
	 * <br>
	 * 如:excel导入订单信息, getReferenceColumn返回{'purchaseNo','vendorNo'}, 表示按照订单号和供应商编码合并订单明细信息.
	 */
//	abstract protected List<String> getReferenceColumn();
	
	/**
	 * 获取目标BO, 从excel读出的每行数据将形成targetBO
	 * @return
	 */
	abstract protected BusinessObject getTargetBO();

	/**
	 * 保存数据
	 * @param boList
	 */
	abstract protected void saveData(List<BusinessObject> boList) throws Exception;

	
	/**
	 * sheet页是否有数据: 第一个sheet页必须有数据
	 * @param sheets
	 * @return
	 */
	private boolean isDataExists(Sheet[] sheets) {
		return sheets[0].getRows() > 0;
	}
	
	/**
	 * excel是否存在
	 * @return
	 */
	private boolean isExlExists() {
		if(getExlFile() == null)
			return false;

		File file = getExlFile().getFile();
        return file != null && file.exists();
	}

	/**
	 * 获取excel的sheet页.
	 * @return
	 * @throws BOException
	 */
	private Sheet[] getSheets() throws BOException{
		Sheet[] sheets = null;
		try {
			sheets = ExcelUtil.getSheets(getExlFile().getFile());
		} catch (QewebException e) {
			BOException boe = new BOException("获取sheet页失败！");
			boe.setQewebException(e);
			throw boe;
		}

		return sheets;
	}

	public FileBOP getExlFile() {
		return exlFile;
	}

	public void setExlFile(FileBOP exlFile) {
		this.exlFile = exlFile;
	}
}
