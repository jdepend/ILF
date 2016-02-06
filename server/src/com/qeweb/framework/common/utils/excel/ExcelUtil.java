package com.qeweb.framework.common.utils.excel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.qeweb.framework.app.action.FileHandleHelp;
import com.qeweb.framework.common.utils.DateUtils;
import com.qeweb.framework.exception.QewebException;

public class ExcelUtil {
	public static final int DefaultColumnWidth = 18;// 导出excel 默认的列宽
	final static public String DEF_SHEET_NAME = "sheet";		//每个sheet页的默认名称
	final static public int TITLE_FONT_SIZE = 12;				//表头字体大小

	/**
	 * 导出Excel
	 * @author sai.deng on 2011-4-12
	 * @param title 文件名及导出的标题
	 * @param tableHeadTexts 表头文本
	 * @param tableHeadWidths 表头文本宽度定义
	 * @param datas 表格数据
	 * @param mergeDatas 合并的数据
	 * @param response 
	 */
	public static void exportExcel(String title, String[] tableHeadTexts, Object[] tableHeadWidths,
			List<Object[]> datas, List<SheetCell> mergeDatas, HttpServletResponse response,HttpServletRequest request) {
		exportExcel(new SheetCell(0,0,title), tableHeadTexts==null?1:tableHeadTexts.length, tableHeadTexts, tableHeadWidths, datas, mergeDatas, response,request);
	}
	
	public static void exportExcel(String title,int columnCount, String[] tableHeadTexts, Object[] tableHeadWidths,
			List<Object[]> datas, List<SheetCell> mergeDatas, HttpServletResponse response,HttpServletRequest request) {
		exportExcel(new SheetCell(0,0,title),columnCount, tableHeadTexts, tableHeadWidths, datas, mergeDatas, response,request);
	}
	
	/**
	 * 导出excel,当导出列标题需要跨行或者跨列时，只要columnCount为0，并将列标题传入mergeDatas里。
	 * @param titleVO 必须包括的属性有：column,row,content 代表从第几列，第几行开始，得到指定的内容
	 * @param columnCount
	 * @param tableHeadTexts
	 * @param tableHeadWidths
	 * @param datas
	 * @param mergeDatas
	 * @param response
	 */
	public static void exportExcel(SheetCell titleVO,int columnCount, String[] tableHeadTexts, Object[] tableHeadWidths,
			List<Object[]> datas, List<SheetCell> mergeDatas, HttpServletResponse response,HttpServletRequest request) {
		exportExcelSetHeader(titleVO.getContent(), response,request);
		WritableWorkbook wwb = null;
		jxl.write.WritableSheet ws = null;
		try {
			wwb = Workbook.createWorkbook(response.getOutputStream()); // 此处建立路径
			ws = wwb.createSheet(titleVO.getContent(), 0); // 建立工作簿
			// 设置标题的列宽
			if (tableHeadWidths != null && tableHeadWidths.length > 0) {
				for (int k = 0; k < tableHeadWidths.length; k++) {
					ws.setColumnView(k, Integer.parseInt(tableHeadWidths[k].toString()));
				}
			} else {
				if(tableHeadTexts != null && tableHeadTexts.length > 0) {
					for (int k = 0; k < columnCount; k++) {
						ws.setColumnView(k, ExcelUtil.DefaultColumnWidth);
					}
				}
			}
			
			ws.mergeCells(titleVO.getColumn(), titleVO.getRow(), columnCount<=1?0:columnCount-1, 3); // 设置标题单元格合并,合并单元格格式:列,行,列,行,
			jxl.write.Label titleString = new jxl.write.Label(titleVO.getColumn(), titleVO.getRow(), titleVO.getContent(), getDefaultStyle(24, true)); 
			ws.addCell(titleString); // 将标题放入工作簿
			//将表数据加入工作簿
			if (datas != null && datas.size() > 0) {
				// 将表头文本放入工作簿
				if(tableHeadTexts != null && tableHeadTexts.length > 0) {
					for (int i = 0; i < tableHeadTexts.length; i++) {
						jxl.write.Label label = new jxl.write.Label(i, 4, tableHeadTexts[i], getDefaultStyle(10, true)); // 第i列第4行
						ws.addCell(label); 
					}
				}
				for (int j = 0; j < datas.size(); j++) {
					Object[] objs = (Object[]) datas.get(j);
					for (int k = 0; k < objs.length; k++) {
						try {
							jxl.write.Number number = new jxl.write.Number(k, j + 5, 
									Double.valueOf(objs == null ? "" : objs[k] == null ? ""
											: objs[k].toString()).doubleValue(),getDefaultStyle(10, false));
							ws.addCell(number); 
						} catch (Exception e) {
							jxl.write.Label labelb = new jxl.write.Label(k, j + 5,
									objs == null ? "" : objs[k] == null ? ""
											: objs[k].toString(),getDefaultStyle(10, false));
							ws.addCell(labelb); 
						}
					}
				}
			}
			// 合并单元格的内容
			if (mergeDatas != null && mergeDatas.size() > 0) {
				for (SheetCell vo : mergeDatas) {
					if (vo.isMergeCell()) {
						ws.mergeCells(vo.getColumn(), vo.getRow(), vo.getColSpan(), vo.getRowSpan());// 合并单元格的内容
					}
					try {
						jxl.write.Number number = new jxl.write.Number(vo.getColumn(), vo.getRow(), 
								Double.valueOf(vo.getContent().toString()).doubleValue(),getDefaultStyle(10, false,vo.getColour()));
						ws.addCell(number); 
					} catch (Exception e) {
						jxl.write.Label labelb = new jxl.write.Label(vo.getColumn(), vo.getRow(), 
								vo.getContent().toString(),getDefaultStyle(10, false,vo.getColour()));
						ws.addCell(labelb);
					}
				}
			}
			wwb.write(); // 写入Exel工作表
			wwb.close(); // 关闭Excel工作薄对象
			wwb = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wwb != null) {
				try {
					wwb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected static void exportExcelSetHeader(String title,
			HttpServletResponse response, HttpServletRequest request) {
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("pragma", "no-cache");
			/*response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((title + ".xls").getBytes(), "iso8859-1"));
			*/
			response.setCharacterEncoding("UTF-8");
			String fileName = title + DateUtils.getNowStr() + ".xls";
		    response.addHeader("Content-disposition", "attachment;filename=" + getEncodedFileName(request,fileName));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getEncodedFileName(HttpServletRequest request,
			String fileName) {
		if (fileName == null) {
			return null;
		}
		String encodedOutputFileName = fileName;
		String agent = request.getHeader("user-agent");
		if (agent != null) {
			try {
				if ((agent.indexOf("Firefox") >= 0) || (agent.indexOf("Gecko") >= 0)) {
					encodedOutputFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				if (agent.indexOf("MSIE") >= 0) {
					encodedOutputFileName = URLEncoder.encode(fileName, "UTF-8");
				}
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}
		return encodedOutputFileName;
	}
	
	protected static CellFormat getDefaultStyle(int fontSize, boolean isBold) {
		return ExcelUtil.getDefaultStyle(fontSize, isBold, null);
	}
	
	public static CellFormat getDefaultStyle(int fontSize, boolean isBold, jxl.format.Colour colour) {
		WritableFont columnFont = new WritableFont(WritableFont
				.createFont("宋体"), fontSize, isBold ? WritableFont.BOLD
				: WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
				jxl.format.Colour.BLACK);
		WritableCellFormat mainFmt = new WritableCellFormat(columnFont);
		try {
			mainFmt.setAlignment(Alignment.CENTRE);// 水平对齐
			mainFmt.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			if(colour != null) {
				mainFmt.setBackground(colour);// 背景色
			}
			mainFmt.setBorder(Border.ALL, BorderLineStyle.THIN);// 线条
			mainFmt.setWrap(true); // 是否换行
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mainFmt;
	}
	
	/**
	 * 获取excel的sheet页
	 * @param excel
	 * @return
	 * @throws QewebException
	 */
	public static Sheet[] getSheets(File excel) throws QewebException {
		Workbook workbook = null;
	
		try {
			workbook = Workbook.getWorkbook(excel);
		} catch (BiffException e) {
			e.printStackTrace();
			throw new QewebException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new QewebException("读取文件失败！");
		}
		
		return workbook.getSheets();		
	}
	
	/**
	 * 生成excel（无内容，有标题）
	 * @param templateFileName
	 * @param tmplateDir
	 * @param fileMap 模版结构 key：列头标注（字段名称）；value：列头内容（字段国际化）
	 * @return
	 */
	public static File createExcel(String fileName,
			String fileDir, Map<String, String> fileMap) {

		FileOutputStream os = null;			
		WritableWorkbook workbook = null;
		
		try {
			//指定模版存放文件
			File file = new File(FileHandleHelp.makdSaveDir(fileDir), fileName);
			os = new FileOutputStream(file);
			workbook = Workbook.createWorkbook(os);
				
			createEmptySheet(fileMap, workbook);
			workbook.write(); 
			
			return file;
		} catch (WriteException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
		finally {
			try {
				workbook.close();
				os.close();
			} catch (WriteException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 创建sheet（无内容，有标题）
	 * @param fileMap
	 * @param workbook
	 * @return
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	private static WritableSheet createEmptySheet(Map<String, String> fileMap,
			WritableWorkbook workbook) throws RowsExceededException, WriteException {
		WritableSheet sheet = workbook.createSheet(DEF_SHEET_NAME + 1, 1);
		if(fileMap == null || fileMap.isEmpty())
			return sheet;
		
		CellFormat cellFormat = getTitleStyle();
		//添加名为"序号"的列头
		Label cell = new Label(0, 0, "序号", cellFormat);
		sheet.addCell(cell);
		int col = 1;
		for(Entry<String, String> entry :  fileMap.entrySet()) {
			//添加单元格， Label(列,行,数据,单元格格式)
			cell = new Label(col, 0, entry.getValue(), cellFormat);
			//添加批注
			WritableCellFeatures cellFeatures = new WritableCellFeatures();
			cellFeatures.setComment(entry.getKey());
			cell.setCellFeatures(cellFeatures);
			sheet.addCell(cell);
			col++;
		}
		
		return sheet;
	}
	
	protected static CellFormat getTitleStyle() {
		return ExcelUtil.getDefaultStyle(TITLE_FONT_SIZE, true, jxl.format.Colour.GREY_25_PERCENT);
	}
}
