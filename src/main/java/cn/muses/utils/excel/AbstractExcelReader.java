package cn.muses.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.muses.utils.generic.GenericUtil;

/**
 * @author miaoqiang
 * @date 2020/4/8.
 */
public abstract class AbstractExcelReader<T, R> implements IExcelReader<T, R> {
    /**
     * 总行数
     */
    private int totalRows;

    /**
     * 起始行总列数
     */
    private int totalCells;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 得到总行数
     *
     * @return
     */
    public int getTotalRows() {
        return this.totalRows;
    }

    /**
     * 得到总列数
     *
     * @return
     */
    public int getTotalCells() {
        return this.totalCells;
    }

    /**
     * 得到错误信息
     *
     * @return
     */
    public String getErrorInfo() {
        return this.errorInfo;
    }

    @Override
    public List<T> parse(String filePath, int sheetIndex, int rowStartNum, int rowEndNum) {
        List<List<String>> rows = this.read(filePath, sheetIndex, rowStartNum, rowEndNum);
        return rows.stream().map(row -> {
            try {
                Class type = GenericUtil.getSuperClassGenericType(this.getClass());
                T instance = (T) type.newInstance();
                final Field[] fields = type.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    final Field field = fields[i];
                    final cn.muses.utils.excel.Cell annotation = field.getAnnotation(cn.muses.utils.excel.Cell.class);
                    if (annotation == null) {
                        continue;
                    }
                    field.setAccessible(true);
                    final Class<?> fieldType = field.getType();
                    final String cellValue = row.get(annotation.cellnum());
                    Object value;
                    if (fieldType.isAssignableFrom(Integer.class)) {
                        if (StringUtils.isBlank(cellValue)) {
                            continue;
                        }
                        value = new BigDecimal(cellValue).intValue();
                    } else if (fieldType.isAssignableFrom(Long.class)) {
                        if (StringUtils.isBlank(cellValue)) {
                            continue;
                        }
                        value = new BigDecimal(cellValue).longValue();
                    } else if (fieldType.isAssignableFrom(BigDecimal.class)) {
                        if (StringUtils.isBlank(cellValue)) {
                            continue;
                        }
                        value = new BigDecimal(cellValue);
                    } else if (fieldType.isAssignableFrom(String.class)) {
                        value = cellValue;
                    } else {
                        continue;
                    }
                    field.set(instance, value);
                }
                return instance;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return null;
        }).collect(Collectors.toList());
    }

    /**
     * 根据文件名读取excel文件
     *
     * @param filePath
     * @param sheetIndex
     * @param rowStartNum
     * @param rowEndNum
     * @return
     */
    public List<List<String>> read(String filePath, int sheetIndex, int rowStartNum, int rowEndNum) {
        List<List<String>> dataLst = new ArrayList<>();

        InputStream is = null;
        try {
            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                System.out.println(errorInfo);

                return null;
            }

            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WdwUtil.isExcel2007(filePath)) {

                isExcel2003 = false;
            }

            /** 调用本类提供的根据流读取的方法 */
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003, sheetIndex, rowStartNum, rowEndNum);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }

        // 返回最后读取的结果
        return dataLst;
    }

    /**
     * 验证excel文件
     *
     * @param filePath
     * @return
     */
    private boolean validateExcel(String filePath) {
        // 检查文件是否存在
        File file;
        if (StringUtils.isBlank(filePath) || (file = new File(filePath)) == null || !file.exists()) {
            this.errorInfo = "文件不存在";
            return false;
        }
        // 检查文件名是否为空或者是否是Excel格式的文件
        if (!(WdwUtil.isExcel2003(filePath) || WdwUtil.isExcel2007(filePath))) {
            this.errorInfo = "文件名不是excel格式";
            return false;
        }

        return true;
    }

    /**
     * 根据流读取Excel文件
     *
     * @param inputStream
     * @param isExcel2003
     * @param sheetIndex
     * @param rowStartNum
     * @param rowEndNum
     * @return
     */
    private List<List<String>> read(InputStream inputStream, boolean isExcel2003, int sheetIndex, int rowStartNum,
        int rowEndNum) {
        List<List<String>> dataLst = null;
        try {
            // 根据版本选择创建Workbook的方式
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb, sheetIndex, rowStartNum, rowEndNum);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataLst;
    }

    /**
     * 读取数据
     *
     * @param wb
     * @param sheetIndex
     * @param rowStartNum
     * @param rowEndNum
     * @return
     */
    private List<List<String>> read(Workbook wb, int sheetIndex, int rowStartNum, int rowEndNum) {
        List<List<String>> dataLst = new ArrayList<>();

        // 得到指定sheet
        Sheet sheet = wb.getSheetAt(sheetIndex);

        // 得到Excel指定sheet的行数
        this.totalRows = sheet.getPhysicalNumberOfRows();

        // 得到Excel起始行的列数
        Row startRow;
        if ((startRow = sheet.getRow(rowStartNum)) != null) {
            this.totalCells = startRow.getPhysicalNumberOfCells();
        }

        /** 循环Excel的行 */
        for (int r = rowStartNum; r < this.totalRows && r <= rowEndNum; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<>();
            /** 循环Excel的列 */
            for (int c = 0; c < this.totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        // 数字
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            cellValue = cell.getNumericCellValue() + "";
                            break;
                        // 字符串
                        case HSSFCell.CELL_TYPE_STRING:
                            cellValue = cell.getStringCellValue();
                            break;
                        // Boolean
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        // 公式
                        case HSSFCell.CELL_TYPE_FORMULA:
                            cellValue = cell.getCellFormula() + "";
                            break;
                        // 空值
                        case HSSFCell.CELL_TYPE_BLANK:
                            cellValue = "";
                            break;
                        // 故障
                        case HSSFCell.CELL_TYPE_ERROR:
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }

                rowLst.add(cellValue);
            }

            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }

        return dataLst;
    }
}

/**
 * @author miaoqiang
 * @date 2020/4/8.
 */
class WdwUtil {
    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
