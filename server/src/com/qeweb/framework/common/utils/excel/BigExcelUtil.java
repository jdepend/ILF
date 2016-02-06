package com.qeweb.framework.common.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.qeweb.framework.common.utils.ContainerUtil;
import com.qeweb.framework.common.utils.MathUtil;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 导出excel<br>
 * 将大量数据导出到Excel是一项常用功能，但是一次导出的数据过多时将会导致速度缓慢并且造成内存溢出，
 * 以下是一份测试报告：
 * 数据/sheet	JXL
	10000		42270
	5000		46270
	3000		47860
 * 多分sheet能一定程度上减少内存的使用，但是均因为程序中创建的Cell无法释放，消耗大量内存，导致OutOfMemery错误。
 * JXL导出10000条数据的时间约为30秒，每个sheet页1000条数据时最多导出42270条数据。
 * 为了能够有效的导出大量数据，采用分excel的方式导出，即将数据导出到多个excel中，每关闭一个excel便能释放一次内存。
 * 
 *
 */
class BigExcelUtil {
	final static public int SHEET_DATA_COUNT = 1000;			//每个sheet的数据量
	final static public int SHEET_COUNT = 5;					//每个excel的最大sheet页数
	final static public int XLS_DATA_COUNT 						//每个excel容纳的最大数据量
		= SHEET_DATA_COUNT * SHEET_COUNT;						
	final static public String DEF_SHEET_NAME = "sheet";		//每个sheet页的默认名称
	final static public int DEF_FONT_SIZE = 10;					//默认字体大小
	final static public int TITLE_FONT_SIZE = 12;				//表头字体大小

	/**
	 * 将datas导出到file
	 * @param datas 待导出到excel的全部数据,datas的最大数量不能超过XLS_DATA_COUNT,超出的数据将会丢失
	 * @param title excel数据的表头
	 * @param exlSeq excel的序号，导出时可能会导出多个excel
	 * @param file
	 * @return
	 */
	public static boolean exportExcel(List<Object[]> datas, String[] title, int exlSeq, File file) {
		if(!checkFile(file))
			return false;
		
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return exportExcel(datas, title, exlSeq, os);
	}
	
	/**
	 * 导出单个excel
	 * @param datas 待导出到excel的全部数据,datas的最大数量不能超过XLS_DATA_COUNT,超出的数据将会丢失
	 * @param title excel数据的表头
	 * @param os	输出流
	 * 
	 */
	public static boolean exportExcel(List<Object[]> datas, String[] title, OutputStream os) {
		return exportExcel(datas, title, 1, os);
	}
	/**
	 * 导出excel
	 * @param datas 待导出到excel的全部数据,datas的最大数量不能超过XLS_DATA_COUNT,超出的数据将会丢失
	 * @param title excel数据的表头
	 * @param exlSeq excel的序号，导出时可能会导出多个excel，
	 * 		第二个excel需要设置sequence以方便展示。exlSeq从1开始
	 * @param os	输出流
	 * 
	 */
	@SuppressWarnings("unchecked")
	private static boolean exportExcel(List<Object[]> datas, String[] title, int exlSeq, OutputStream os) {
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(os);
			if(ContainerUtil.isNull(datas)) {
				createSheet(title, 0, workbook);
				workbook.write(); 
				return true;
			}
			
			for(int sheetIdx = 0; sheetIdx < getSheetCount(datas); sheetIdx++) {
				int dataBegin = SHEET_DATA_COUNT * sheetIdx;
				int dataEnd = dataBegin +  SHEET_DATA_COUNT;
				WritableSheet sheet = createSheet(title, sheetIdx, workbook);
				addDataInSheet(ContainerUtil.getSubList(datas, dataBegin, dataEnd), 
						sheetIdx, 
						SHEET_DATA_COUNT * sheetIdx + XLS_DATA_COUNT * (exlSeq - 1),
						sheet);
			}

			workbook.write(); 
			return true;
		} catch (WriteException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		finally {
			try {
				workbook.close();
				os.close();
			} catch (WriteException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	
	/**
	 * 取得导出的excel的sheet页数（该excel共有几个sheet页）
	 * @param datas 待导出到excel的全部数据
	 * @return
	 */
	private static int getSheetCount(List<Object[]> datas) {
		int dataSize = datas.size();
		return dataSize > XLS_DATA_COUNT ? 
				SHEET_COUNT : MathUtil.getDivision(dataSize, SHEET_DATA_COUNT);
	}
	
	/**
	 * 创建sheet，并添加列头
	 * @param title		列头的名称
	 * @param sheetIdx  sheet的序号
	 * @param workbook
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static WritableSheet createSheet(String[] title, int sheetIdx, WritableWorkbook workbook)
		throws RowsExceededException, WriteException {
		
		WritableSheet sheet = workbook.createSheet(DEF_SHEET_NAME + sheetIdx, sheetIdx);
		if(title == null)
			return sheet;
		
		CellFormat cellFormat = getTitleStyle();
		//添加名为"序号"的列头
		Label cell = new Label(0, 0, "序号", cellFormat);
		sheet.addCell(cell);
		for(int col = 0; col < title.length; col++) {
			//添加单元格， Label(列,行,数据,单元格格式)
			cell = new Label(col + 1, 0, title[col], cellFormat);
			sheet.addCell(cell);
		}
		
		return sheet;
	}
	
	/**
	 * 向sheet页添加数据
	 * @param sheetDatas	待导出到该sheet的全部数据
	 * @param sheetIdx	sheet的序号
	 * @param sequence 第一个sheet页第一行数据的序号，导出时可能会导出多个excel，
	 * 		第二个excel需要设置sequence以方便展示
	 * @param workbook  
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	private static void addDataInSheet(List<Object[]> sheetDatas, int sheetIdx, int sequence, WritableSheet sheet)
		throws RowsExceededException, WriteException {
		
		CellFormat cellFormat = getDefaultStyle();
		
		//将数据加入工作簿
		int length = sheetDatas.size();
		for(int row = 0; row < length; row++) {
			//添加单元格， Label(列,行,数据,单元格格式)
			//添加row行第一列，内容是该行数据的序号
			Label cell = new Label(0, row + 1, (++sequence) + "", cellFormat);
			sheet.addCell(cell);
			
			Object[] data = sheetDatas.get(row);
			for(int col = 0; col < data.length; col++) {
				//添加单元格， Label(列,行,数据,单元格格式)
				cell = new Label(col + 1, row + 1, getCellData(data[col]), cellFormat);
				sheet.addCell(cell);
			}
		}
	}
	
	/**
	 * 获取默认单元格格式： 字体10，其它格式为excel默认格式
	 * @return CellFormat
	 */
	private static CellFormat getDefaultStyle() {
		return ExcelUtil.getDefaultStyle(DEF_FONT_SIZE, false, null);
	}

	/**
	 * 表头的样式
	 * @return
	 */
	private static CellFormat getTitleStyle() {
		return ExcelUtil.getDefaultStyle(TITLE_FONT_SIZE, true, jxl.format.Colour.GREY_25_PERCENT);
	}
	
	private static String getCellData(Object obj) {
		return obj == null ? "" : obj + ""; 
	}
	
	/**
	 * file是否是excel文件
	 * @param file
	 * @return
	 */
	private static boolean checkFile(File file) {
		if(file == null || file.isDirectory()) 
			return false;

		return file.getName().lastIndexOf(".xls") != -1 
			|| file.getName().lastIndexOf(".xlsx") != -1;
	}
}
